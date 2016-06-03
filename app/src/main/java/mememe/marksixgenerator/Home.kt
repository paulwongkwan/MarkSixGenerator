package mememe.marksixgenerator

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.GridLayoutManager
import android.view.View
import android.view.WindowManager
import android.widget.SeekBar
import android.widget.Toast
import com.gregacucnik.EditableSeekBar
import kotlinx.android.synthetic.main.activity_home.*
import mememe.marksixgenerator.adapter.NumBoxAdapter
import okhttp3.*
import java.io.IOException
import kotlin.comparisons.compareBy

//TODO: Add History
//TODO: Old marksix result

class Home : AppCompatActivity() {

    internal val addr = "https://www.random.org/sequences/?"
    internal var client = OkHttpClient()
    internal val numList = mutableListOf<String>()

    var num : Int = 7
    var min : Int = 1
    var max : Int = 49

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home)

        numberresultbox.layoutManager = GridLayoutManager(this@Home, 3)
        numberresultbox.adapter = NumBoxAdapter(numList)

        reloadNumBtn.setOnClickListener { genRandNum() }
    }

    override fun onResume() {
        super.onResume()

        genRandNum()

        ballAmountBar.setOnEditableSeekBarChangeListener(object : EditableSeekBar.OnEditableSeekBarChangeListener{
            override fun onStopTrackingTouch(seekBar: SeekBar?) { }
            override fun onEnteredValueTooLow() { }
            override fun onEditableSeekBarProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) { }

            override fun onEditableSeekBarValueChanged(value: Int) {
                num = value
                genRandNum()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) { }
            override fun onEnteredValueTooHigh() { }
        })
    }

    fun genRandNum() {
        numList.clear()
        numberresultbox.adapter.notifyDataSetChanged()

        getNumberProgressBar.visibility = View.VISIBLE

        val request = Request.Builder().url("${addr}min=$min&max=$max&col=1&base=10&format=plain&rnd=new").build()

        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@Home, "Connection Fail", Toast.LENGTH_LONG).show()
                    getNumberProgressBar.visibility = View.GONE
                }

            }

            @Throws(IOException::class)
            override fun onResponse(call: Call, response: Response) {
                var result : List<String> = response.body().string().trim().split("\n")
                result = result.subList(0, num)
                result = result.sorted()

                numList.clear()
                runOnUiThread { numberresultbox.adapter.notifyDataSetChanged() }

                for(r in result){
                    numList.add(r)
                    runOnUiThread { numberresultbox.adapter.notifyItemInserted(numList.size + 1) }
                }

                runOnUiThread { getNumberProgressBar.visibility = View.GONE }
            }
        })
    }
}
