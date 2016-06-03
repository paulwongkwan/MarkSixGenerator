package mememe.marksixgenerator.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.balltextbox.view.*
import mememe.marksixgenerator.R

/**
 * Created by MEMEME on 3/6/2016.
 */

open class NumBoxAdapter(val items: List<String>) : RecyclerView.Adapter<NumBoxAdapter.NumBoxViewHolder>(){
    internal val blue = 0;
    internal val red = 1;
    internal val green = 2;

    internal val blueList : MutableList<String> = mutableListOf("34", "14", "15", "25", "26", "36", "37", "46", "48", "31", "41", "42", "9", "10", "20")
    internal val redList : MutableList<String> = mutableListOf("1", "2", "12", "13", "23", "24", "34", "35", "45", "46", "7", "8", "18", "19", "29", "30", "40")
    internal val greenList : MutableList<String> = mutableListOf("11", "21", "22", "32", "33", "43", "44", "5", "6", "16", "17", "27", "28", "38", "39", "49")

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NumBoxViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.balltextbox, parent, false)
        if(viewType == red){
            view.ballImage.setImageResource(R.drawable.ballred)
        }
        if(viewType == green){
            view.ballImage.setImageResource(R.drawable.ballgreen)
        }
        return NumBoxViewHolder(view)
    }
    override fun onBindViewHolder(holder: NumBoxViewHolder, position: Int) {
        holder.numBox.ballnumber.text = items[position]
    }

    override fun getItemCount(): Int = items.size

    override fun getItemViewType(position: Int) : Int{
        var result = blue

        if(redList.contains(items[position])){
            result = red
        }

        if(greenList.contains(items[position])){
            result = green
        }

        return result
    }

    class NumBoxViewHolder(val numBox: View) : RecyclerView.ViewHolder(numBox)
}
