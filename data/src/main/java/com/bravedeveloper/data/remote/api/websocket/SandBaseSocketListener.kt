package com.bravedeveloper.data.remote.api.websocket

import android.util.Log
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString

class SandBaseSocketListener: WebSocketListener() {

    companion object{
        const val tag = "WEBSOCKET"
    }

    override fun onOpen(webSocket: WebSocket, response: Response) {
        Log.d(tag, "Open $response")
    }

    override fun onMessage(webSocket: WebSocket, text: String) {
        Log.d(tag, "Mes $text")
    }

    override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
        Log.d(tag,"MesB $bytes")
    }

    override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
        Log.d(tag, "Fail $t $response")
    }

    override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(tag, "Close $reason")
    }

    override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
        Log.d(tag, reason)
    }
}