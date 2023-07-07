package com.purabmodi.devhacksapp.ui.fragments

import android.app.usage.UsageStats
import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.purabmodi.devhacksapp.adapter.WellbeingAdapter
import com.purabmodi.devhacksapp.data.models.App
import com.purabmodi.devhacksapp.databinding.FragmentWellbeingBinding
import com.purabmodi.devhacksapp.utils.utils
import java.time.LocalDate
import java.util.Calendar
import java.util.Collections
import java.util.concurrent.TimeUnit


class WellbeingFragment : Fragment() {
    private var _binding: FragmentWellbeingBinding? = null
    private val binding get() = _binding!!
    private var totalTime = 0L
    var appList = ArrayList<App>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentWellbeingBinding.inflate(layoutInflater, container, false)

        init()

        return binding.root
    }

    private fun init() {
        val usageStatsManager =
            getSystemService(requireContext(), UsageStatsManager::class.java) as UsageStatsManager

        // Define the time range for which you want to retrieve usage statistics
        val calendar = Calendar.getInstance()
        val localDate = LocalDate.now()
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth, 0, 0, 0)
        calendar.set(Calendar.MILLISECOND, 0)
//        calendar.add(Calendar.DAY_OF_WEEK,-1) // 1 day ago
        val startTime = calendar.timeInMillis
        calendar.set(localDate.year, localDate.monthValue - 1, localDate.dayOfMonth, 23, 59, 59)
        calendar.set(Calendar.MILLISECOND, 999)
        val endTime = calendar.timeInMillis

        // Retrieve the usage statistics using queryUsageStats()
        val usageStatsList =
            usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)

        for (usageStats in usageStatsList) {
            val packageName = usageStats.packageName
            val totalTimeInForeground = usageStats.totalTimeInForeground
            try {
                val packageNames = packageName.split("\\.".toRegex())

                var appName = packageNames[packageNames.size - 1].trim()

                var check = utils().checkPackages(packageName)
                if (check != "") {
                    Log.d("USAGESTATSCHECK", "init: $check")
                    appName = check
                }



                if (totalTimeInForeground > 0) {
                    var min = millisecondsToMinutes(totalTimeInForeground)
                    Log.d(
                        "USAGESTATSCHECK",
                        "AppName = $appName PackageName = $packageName & Total Time = $min minutes"
                    )
                    totalTime += usageStats.totalTimeInForeground
                    var appTotalTime = getTotalTime(usageStats.totalTimeInForeground)
                    appList.add(App(appName, appTotalTime, usageStats.totalTimeInForeground))
                }
            } catch (e: PackageManager.NameNotFoundException) {
                e.printStackTrace()
            }

        }

        var wellbeingAdapter = WellbeingAdapter(onClick = { onclick() })
        binding.wellbeingRC.layoutManager = LinearLayoutManager(requireContext())
        binding.wellbeingRC.adapter = wellbeingAdapter

        val timeComparator = Comparator<App> { z1, z2 ->
            z2.totalTime.compareTo(z1.totalTime)
        }

        Collections.sort(appList, timeComparator)

        wellbeingAdapter.setItems(appList)

        binding.totalTime.text = getTotalTime(totalTime)

    }

    private fun onclick() {
        Toast.makeText(requireContext(), "clicked", Toast.LENGTH_SHORT).show()
    }

    fun getTotalTime(mili: Long): String {
        var millis = mili
        require(millis >= 0) { "Duration must be greater than zero!" }

        val hours: Long = TimeUnit.MILLISECONDS.toHours(millis)
        millis -= TimeUnit.HOURS.toMillis(hours)
        val minutes: Long = TimeUnit.MILLISECONDS.toMinutes(millis)
        millis -= TimeUnit.MINUTES.toMillis(minutes)
        val seconds: Long = TimeUnit.MILLISECONDS.toSeconds(millis)
        Log.d("USAGESTATSCHECK", "$hours h $minutes m $seconds s")
        return "$hours h $minutes m $seconds s"
    }

    private fun millisecondsToMinutes(milliseconds: Long): Long {
        return milliseconds / 1000 / 60
    }

}