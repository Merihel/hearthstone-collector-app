package com.example.lpiem.hearthstonecollectorapp.Interface;

import com.google.gson.JsonObject;

interface InterfaceCallBackSync {
    fun onWorkSyncDone(result: JsonObject)
    fun onWorkSyncDone2(result: JsonObject)
}
