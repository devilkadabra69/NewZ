package com.example.newz.Adapter

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.Request
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.example.newz.ModelClass.Article
import com.example.newz.R
import kotlinx.coroutines.withContext
import java.time.format.DateTimeFormatter

class NewsRVadapter(val context: Context):RecyclerView.Adapter<NewsRVadapter.holder>() {
    inner class holder(val itemview: View):RecyclerView.ViewHolder(itemview){
        val rcv_title=itemview.findViewById<TextView>(R.id.rcv_newstitle)
        val rcv_author=itemview.findViewById<TextView>(R.id.rcv_author)
        val rcv_publishedAt=itemview.findViewById<TextView>(R.id.rcv_publishedAt)
        val rcv_newsimage=itemview.findViewById<ImageView>(R.id.rcv_newsimageview)
    }
    private var diffutilcallback=object :DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url==newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem==newItem
        }

    }
    var differ=AsyncListDiffer(this,diffutilcallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): holder {
        return holder(LayoutInflater.from(parent.context).inflate(R.layout.rcv_skeleton,parent,false))
    }

    override fun onBindViewHolder(holder: holder, position: Int) {
        if(differ.currentList.isNotEmpty()){
            if(differ.currentList.get(position).author!=null){
                holder.rcv_author.text=differ.currentList.get(position).author
            }else{
                holder.rcv_author.text="N/A"
            }
            if(differ.currentList.get(position).title!=null){
                holder.rcv_title.text=differ.currentList.get(position).title
            }else{
                holder.rcv_title.text="N/A"
            }
            if(differ.currentList.get(position).publishedAt!=null){
                val unformatedString=differ.currentList[position].publishedAt.toString()
                val inputformatter :DateTimeFormatter=DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:SSS'Z'")
                val outputformatter :DateTimeFormatter=DateTimeFormatter.ofPattern("dd-MM-yyyy")
                try {
                    val d =inputformatter.parse(unformatedString)
                    holder.rcv_publishedAt.text=outputformatter.format(d)

                }catch (
                    E:Exception
                ){
                    Log.d("ADAPTER",E.printStackTrace().toString())
                }
            }else{
                holder.rcv_publishedAt.text="N/A"
            }
            if(differ.currentList.get(position).urlToImage!=null)
                Glide.with(holder.itemView.context).load(differ.currentList.get(position).urlToImage).listener(object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.rcv_newsimage.setImageResource(R.drawable.broken)
                        holder.rcv_newsimage.scaleType=ImageView.ScaleType.CENTER_CROP
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                })
                    .into(holder.rcv_newsimage)
            else {
                holder.rcv_newsimage.setImageResource(R.drawable.broken)
                holder.rcv_newsimage.scaleType=ImageView.ScaleType.CENTER_CROP
            }
        }
        val article=differ.currentList.get(position)
        holder.itemview.setOnClickListener {
            onItemClickListner?.let {it(article)}
        }
        holder.itemview.setOnLongClickListener {
            onItemLongCLickListner?.let {it(article)}
            true
        }
    }

    override fun getItemCount(): Int {
       return differ.currentList.size
    }
//    private fun onclick(holder: holder){
//        holder.itemview.setOnClickListener {
//           Toast.makeText(context,"Item Clicked :${holder.adapterPosition+1}",Toast.LENGTH_SHORT).show()
//        }
//    }
//    private fun onlongclick(holder:holder){
//        holder.itemview.setOnLongClickListener {
//            Toast.makeText(context,"Item Long Clicked :${holder.adapterPosition+1}",Toast.LENGTH_SHORT).show()
//            true
//        }
//    }
    private var onItemLongCLickListner:((Article)->Boolean)?=null
    private var onItemClickListner:((Article)->Unit)?=null

    fun setOnItemClickListner(listner:(Article)->Unit){
        onItemClickListner=listner
    }
    fun setOnItemLongClickListner(listner: (Article) -> Boolean){
        onItemLongCLickListner=listner
    }
}