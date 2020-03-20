package com.example.firebaseapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray

class MyRecyclerAdapter (context: Context, val dataSource: JSONArray) : RecyclerView.Adapter<MyRecyclerAdapter.Holder>() {

    private val thiscontext : Context = context

    class Holder(view : View) : RecyclerView.ViewHolder(view) {
        private val View = view;

        lateinit var layout : LinearLayout
        lateinit var titleTextView: TextView
        lateinit var descriptionTextView: TextView
        lateinit var priceTextView: TextView

        lateinit var image: ImageView

        fun Holder(){

            layout = View.findViewById<View>(R.id.recyview_layout) as LinearLayout
            titleTextView = View.findViewById<View>(R.id.tv_name) as TextView
            descriptionTextView = View.findViewById<View>(R.id.tv_lastname) as TextView
            priceTextView =  View.findViewById<View>(R.id.tv_position) as TextView


            image = View.findViewById<View>(R.id.imgV) as ImageView

        }
    }

    override fun onCreateViewHolder(parent : ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.recy_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return dataSource.length()

    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.Holder()

        holder.titleTextView.setText( dataSource.getJSONObject(position).getString("title").toString() )
        holder.descriptionTextView.setText( dataSource.getJSONObject(position).getString("description").toString() )
        holder.priceTextView.setText( dataSource.getJSONObject(position).getString("price").toString() )


        Glide.with(thiscontext)
            .load(dataSource.getJSONObject(position).getString("image").toString())
            .into(holder.image)

        //ประกาศตัวแปร DatabaseReference รับค่า Instance และอ้างถึง path ที่เราต้องการใน database
        val mRootRef = FirebaseDatabase.getInstance().getReference()

        //อ้างอิงไปที่ path ที่เราต้องการจะจัดการข้อมูล ตัวอย่างคือ users และ messages
        val mUsersRef = mRootRef.child("users")
        val mMessagesRef = mRootRef.child("messages")

        holder.layout.setOnClickListener{

            //เรียก support manager เพื่อทำการส่งหน้าด้วย fragment
            val manager = (holder.itemView.context as FragmentActivity).supportFragmentManager


            val detail = fragment_detail().newInstance(dataSource.getJSONObject(position).getString("image").toString() ,holder.titleTextView.text.toString(),dataSource.getJSONObject(position).getString("description").toString(),holder.priceTextView.text.toString())
            val transaction : FragmentTransaction = manager!!.beginTransaction()
            transaction.replace(R.id.layout,detail,"detail")
            transaction.addToBackStack("detail")
            transaction.commit()

            Toast.makeText(thiscontext,"You click "+ holder.titleTextView.text.toString(), Toast.LENGTH_SHORT).show()



            val friendlyMessage = FriendlyMessage(holder.titleTextView.text.toString(),holder.priceTextView.text.toString());
            mMessagesRef.push().setValue(friendlyMessage);



        }

    }
    data class FriendlyMessage(
        var room: String? = "",
        var price: String? = ""
    )

}
