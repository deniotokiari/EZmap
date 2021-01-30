package by.deniotokiari.ezmap

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import by.deniotokiari.utils.kotlin.TitleUtil

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.text_view).text = TitleUtil().getTitle()
    }
}