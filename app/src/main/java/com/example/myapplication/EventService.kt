package com.example.myapplication

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.compose.ui.platform.LocalContext
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class EventService : Service() {
    companion object{
        private const val TAG = "EventService"
        private const val DELAY_MILIS : Long =10000L
    }

    private lateinit var job: Job


    @OptIn(DelicateCoroutinesApi::class)
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d(TAG,"starting service...")
        val context = applicationContext

        job = GlobalScope.launch(Dispatchers.IO) {
            while (true){
                val eventList = fetchEvents()

                eventList?.forEach {

                    val intent2 = Intent().apply {
                        action = MyIntent.SHOW_NOTIFICATION
                        `package` = MyIntent.PACKAGE
                        putExtra(MyIntent.DEVICE_ID, it.deviceId)
                    }
                    sendOrderedBroadcast(intent2, null)

                }
                delay(DELAY_MILIS)
            }
        }

        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onDestroy() {
        Log.d(TAG,"stopping")
        if(job.isActive){
            job.cancel()
        }
        super.onDestroy()
    }

    private fun fetchEvents(): List<EventData>? {
        Log.d(TAG,"FETCHING EVENT")
        val url = "${BuildConfig.API_BASE_URL}devices/events"
        val connection = (URL(url).openConnection() as HttpURLConnection).also{
            it.requestMethod = "GET"
            it.setRequestProperty("Accept","application/json")
            it.setRequestProperty("Content-Type","text/event-steam")
            it.doInput=true
        }
        val responseCode = connection.responseCode
        if(responseCode == HttpURLConnection.HTTP_OK){
            val stream = BufferedReader(InputStreamReader(connection.inputStream))
            var line: String?
            val response = StringBuffer()
            val eventList = arrayListOf<EventData>()
            while (stream.readLine().also{ line = it } != null){
                when{
                    line!!.startsWith("data:") -> {
                        response.append(line!!.substring(5))
                    }
                    line!!.isEmpty() -> {
                        val gson = Gson()
                        Log.d(TAG, "Bro (${response})")
                        val event = gson.fromJson<EventData>(
                            response.toString(),
                            object : TypeToken<EventData?>() {}.type
                        )
                        eventList.add(event)
                        response.setLength(0)
                    }
                }
            }
            stream.close()
            Log.d(TAG,"new events found")
            return eventList
        }else{
            Log.d(TAG,"conection error")
            return null
        }

    }


}