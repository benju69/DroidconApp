package co.touchlab.droidconandroid

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.content.Intent
import android.support.v4.app.Fragment
import co.touchlab.android.threading.eventbus.EventBusExt
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.view.LayoutInflater
import android.view.ViewGroup
import co.touchlab.droidconandroid.data.Event
import android.widget.ArrayAdapter
import android.content.Context
import android.view
import android.support.v4.app.LoaderManager.LoaderCallbacks
import co.touchlab.android.threading.loaders.networked.DoubleTapResult
import co.touchlab.droidconandroid.data.UserAccount
import android.support.v4.content.Loader
import co.touchlab.android.threading.loaders.networked.DoubleTapResult.Status
import co.touchlab.droidconandroid.utils.Toaster

/**
 * Created by kgalligan on 8/1/14.
 */
class ScheduleDataActivity : FragmentActivity()
{
    class object
    {
        val USER_ID = "USER_ID"

        fun callMe(a: Activity)
        {
            val i = Intent(a, javaClass <ScheduleDataActivity> ())
            a.startActivity(i)
        }

    }

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super < FragmentActivity >.onCreate(savedInstanceState)
        setContentView(R.layout.activity_schedule_data)
    }
}

class ScheduleDataFragment() : Fragment()
{
    var eventList: ListView? = null
    var adapter: EventAdapter? = null

    override fun onCreateView(inflater: LayoutInflater?, container: view.ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val view = inflater!!.inflate(R.layout.fragment_schedule_data, null)!!
        eventList = view.findViewById(R.id.eventList) as ListView
        eventList!!.setOnItemClickListener(object : AdapterView.OnItemClickListener
        {
            override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long)
            {
                val event = adapter!!.getItem(position)
                EventDetailActivity.callMe(getActivity()!!, event!!.id)
            }
        })

        getLoaderManager()!!.initLoader(0, null, this.ScheduleDataLoaderCallbacks())

        return view
    }

    inner class ScheduleDataLoaderCallbacks() : LoaderCallbacks<List<Event>>
    {
        override fun onCreateLoader(id: Int, args: Bundle?): Loader<List<Event>>?
        {
            return ScheduleDataLoader(getActivity()!!)
        }

        override fun onLoadFinished(loader: Loader<List<Event>>?, data: List<Event>?)
        {
            if (data == null || data.empty)
            {
                Toaster.showMessage(getActivity(), "NoData")
            }
            else
            {
                adapter = EventAdapter(getActivity()!!, data)
                eventList!!.setAdapter(adapter!!)
            }

        }
        override fun onLoaderReset(loader: Loader<List<Event>>?)
        {

        }
    }

    private inner class EventAdapter(context: Context, objects: List<Event>) : ArrayAdapter<Event>(context, android.R.layout.simple_list_item_1, objects)
    {

        override fun getView(position: Int, v: View?, parent: view.ViewGroup): View?
        {
            var convertView = v
            if (convertView == null)
            {
                convertView = LayoutInflater.from(getContext()!!).inflate(R.layout.default_schedule_list_row, null)
            }

            val eventName = convertView!!.findViewById(R.id.eventName) as TextView
            val eventDescription = convertView!!.findViewById(R.id.eventDescription) as TextView
            val rsvpStatus = convertView!!.findViewById(R.id.rsvpStatus) as TextView

            val event = getItem(position)

            eventName.setText(event!!.name)
            eventDescription.setText(event.description)
            rsvpStatus.setText(if (event.rsvpUuid == null) "No" else "Yes")

            return convertView
        }


    }
}