package com.example.accessibilityv1

import android.content.Context
import android.speech.tts.TextToSpeech
import android.util.Log
import java.util.*

class TextToVoice constructor(context: Context) {
    var tts: TextToSpeech

    init {
        tts = TextToSpeech(context) { status -> onInitTextToSpeech(status) }
    }

    private fun onInitTextToSpeech(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts.setLanguage(Locale("es", "ES"))
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("AA", "The Language specified is not supported!")
            }
        } else {
            Log.e("AA", "Initilization Failed! Status: $status")
        }
    }

    fun destroy() {
        tts.stop()
        tts.shutdown()
    }

    fun speak(text: String) {
        Log.i("AA Speak", text)
        this.speakOut(text);
    }

    private fun speakOut(text: String) {
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
    }
}