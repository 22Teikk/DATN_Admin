package com.teikk.datn_admin.data.service.socket

import android.util.Log
import io.socket.client.Socket
import io.socket.emitter.Emitter
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SocketManager @Inject constructor(
    private val mSocket: Socket,
    private val listeners: SocketListeners
) {

    fun socketConnect() {
        if (!mSocket.connected()) {
            DebounceUtils.debounce100(object : DebounceUtils.DebounceCallback {
                override fun run() {
                    mSocket.on(Socket.EVENT_CONNECT, onConnect)
                    mSocket.on(Socket.EVENT_DISCONNECT, onDisconnect)
                    mSocket.on(Socket.EVENT_CONNECT_ERROR, onConnectError)
                    mSocket.connect()
                }
            })
        }
    }

    fun onNewOrder(callback: () -> Unit) {
        mSocket.on("new_order") { args ->
            val data = args[0] as JSONObject
            callback()
        }
    }

    fun onMessage(callback: () -> Unit) {
        mSocket.on("message") { args ->
            val data = args[0] as JSONObject
            callback()
        }
    }

    fun joinEmployee(employeeId: String) {
        val data = JSONObject()
        data.put("employee_id", employeeId)
        mSocket.emit("employee_join", data)
    }

    fun assignCustomer(customerId: String, employeeId: String) {
        val data = JSONObject()
        data.put("customer_id", customerId)
        data.put("employee_id", employeeId)
        mSocket.emit("assign_customer", data)
    }

    fun sendMessage(customerId: String, employeeId: String, message: String) {
        val data = JSONObject()
        data.put("customer_id", customerId)
        data.put("employee_id", employeeId)
        data.put("message", message)
        mSocket.emit("private_message", data)
    }

    fun socketDisconnect() {
        socketOff()
        mSocket.disconnect()
    }

    private fun socketOn() {
        socketOff()
        mSocket.on("transactionEvent", listeners.onTransactionsListening)
    }


    private fun socketOff() {
        mSocket.off("transactionEvent")
    }

    val onConnect = Emitter.Listener {
        Log.d(
            TAG,
            "SocketManager  isConnected " + mSocket.connected() + " |  isActive  " + mSocket.isActive
        )
        socketOn()
    }
    val onDisconnect = Emitter.Listener {
        Log.d(
            TAG,
            "SocketManager   Disconnected " + mSocket.connected() + " |  isActive  " + mSocket.isActive
        )
        socketOff()
    }
    val onConnectError = Emitter.Listener { args: Array<Any> ->
        Log.d(TAG, "SocketManager Error connecting..." + args[0].toString())
        socketOff()
    }


    companion object {
        private const val TAG = "SocketManager"
    }
}