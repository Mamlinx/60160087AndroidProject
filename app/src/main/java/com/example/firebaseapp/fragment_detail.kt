package com.example.firebaseapp


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide

/**
 * A simple [Fragment] subclass.
 */
class fragment_detail : Fragment() {



    var image_v:String ?= null
    var title_v:String ?= null
    var description_v:String ?= null
    var price_v:String ?= null

    fun newInstance(image:String,title:String,description: String,price:String): fragment_detail {
        val fragment = fragment_detail()
        val bundle = Bundle()

        bundle.putString("image",image);
        bundle.putString("title",title);
        bundle.putString("description",description);
        bundle.putString("price",price);

        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null){
            this.title_v= bundle.getString("title").toString()
            this.image_v = bundle.getString("image").toString()
            this.description_v = bundle.getString("description").toString()
            this.price_v = bundle.getString("price").toString()

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_fragment_detail, container, false)
        val title_view : TextView = view.findViewById(R.id.title_view)
        val description_view : TextView = view.findViewById(R.id.description_view)
        val image_view : ImageView = view.findViewById(R.id.image_view)
       // val price_view : TextView = view.findViewById(R.id.price_view)




        title_view.text = this.title_v
        description_view.text = this.description_v
      //  price_view.text = this.price_v

        Glide.with(activity!!.baseContext).load(image_v).into(image_view)

        val btn_book : Button = view.findViewById(R.id.btn_booking)

        btn_book.setOnClickListener {

            val booking =
                fragment_booking().newInstance(this.title_v.toString(), this.price_v.toString())
            val fm = fragmentManager
            val transaction: FragmentTransaction = fm!!.beginTransaction()
            transaction.replace(R.id.layout, booking, "booking")
            transaction.addToBackStack("booking")
            transaction.commit()
        }
        return view
    }



}
