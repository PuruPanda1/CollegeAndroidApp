package com.purabmodi.devhacksapp.ui.fragments

import android.app.usage.UsageStatsManager
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import com.purabmodi.devhacksapp.databinding.FragmentWellbeingBinding
import java.util.Calendar

class WellbeingFragment : Fragment() {
    private var _binding: FragmentWellbeingBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentWellbeingBinding.inflate(layoutInflater, container, false)

        init()

        return binding.root
    }

    private fun init() {
        val usageStatsManager = getSystemService(requireContext(),UsageStatsManager::class.java) as UsageStatsManager

        // Define the time range for which you want to retrieve usage statistics
        val calendar = Calendar.getInstance()
        val endTime = calendar.timeInMillis
        calendar.add(Calendar.DAY_OF_WEEK, -1) // 1 day ago
        val startTime = calendar.timeInMillis

        // Retrieve the usage statistics using queryUsageStats()
        val usageStatsList = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, startTime, endTime)

        for (usageStats in usageStatsList) {
            val packageName = usageStats.packageName
            val totalTimeInForeground = usageStats.totalTimeInForeground
            try{
//                val packageManager = requireContext().packageManager
//                val appInfo = packageManager.getApplicationInfo(packageName,PackageManager.GET_META_DATA)
//                val appName = packageManager.getApplicationLabel(appInfo).toString()

                val packageNames = packageName.split("\\.".toRegex())
//                val appName = packageName.replace(regex,"").trim()

//                val packageNames: Array<String> = packageName.split("\\.")
                val appName = packageNames[packageNames.size - 1].trim()

                if(totalTimeInForeground>0){
                    var min = millisecondsToMinutes(totalTimeInForeground)
                    Log.d("USAGESTATSCHECK", "AppName = $appName PackageName = $packageName & Total Time = $min minutes")
                }
            }catch (e:PackageManager.NameNotFoundException){
                e.printStackTrace()
            }

            // Process the usage data as per your requirements
            // You can retrieve additional information such as app launch count, last time used, etc. from the UsageStats object
        }


    }
    fun millisecondsToMinutes(milliseconds: Long): Long {
        val minutes = milliseconds / 1000 / 60
        return minutes
    }

}