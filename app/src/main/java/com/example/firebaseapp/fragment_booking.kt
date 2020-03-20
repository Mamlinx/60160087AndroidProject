package com.example.firebaseapp


import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentTransaction

/**
 * A simple [Fragment] subclass.
 */
class fragment_booking : Fragment() {

    var title:String ?= null
    var price:String ?= null

    fun newInstance(title:String,price:String): fragment_booking {
        val fragment = fragment_booking()
        val bundle = Bundle()
        bundle.putString("title",title);
        bundle.putString("price",price);
        fragment.setArguments(bundle)

        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val bundle = arguments
        if(bundle != null){
            this.title = bundle.getString("title").toString()
            this.price = bundle.getString("price").toString()
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_fragment_booking, container, false)

        val title : TextView = view.findViewById(R.id.title)
        val price : TextView = view.findViewById(R.id.price)
        title.text = this.title
        price.text = this.price
        //Glide.with(activity!!.baseContext).load(image).into(image_view)

        val btn_book : Button = view.findViewById(R.id.btn_succes)
        btn_book.setOnClickListener{
            val success = fragment_succes()
            val fm =fragmentManager
            val transaction : FragmentTransaction = fm!!.beginTransaction()

            val builder: AlertDialog.Builder = AlertDialog.Builder(this.context)
            builder.setMessage("ยืนยันที่จะจองห้องพัก?")
            builder.setPositiveButton("ยืนยัน",
                DialogInterface.OnClickListener { dialog, id ->
                    Toast.makeText(this.context, "ขอบคุณค่ะ", Toast.LENGTH_SHORT).show()
                    transaction.replace(R.id.layout,success,"success")
                    transaction.addToBackStack("success")
                    transaction.commit()
                })
            builder.setNegativeButton("ยกเลิก",
                DialogInterface.OnClickListener { dialog, which ->
                    //dialog.dismiss();
                })
            builder.show()
        }
        return view
    }



}
