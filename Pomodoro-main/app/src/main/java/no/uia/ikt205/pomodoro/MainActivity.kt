package no.uia.ikt205.pomodoro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import no.uia.ikt205.pomodoro.util.millisecondsToDescriptiveTime

class MainActivity : AppCompatActivity() {

    lateinit var timer: CountDownTimer
    lateinit var pauseTime: CountDownTimer
    lateinit var startButton: Button
    lateinit var countdownDisplay: TextView
    lateinit var timeSelect: SeekBar
    lateinit var repetitionCounter: EditText
    lateinit var pauseText: TextView
    lateinit var pauseSelect: SeekBar
//

    private var countDownStart: Boolean = false
    private var pauseCountDownStart: Boolean = false

    private var countDownTimeInMs = 15 * 60000L
    private var pauseCountDownInMs = 15 * 60000L
    private val timeTicks = 1000L
    private var repetitoinAmount:Int = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        pauseText = findViewById(R.id.pauseText)
        pauseText.visibility = View.INVISIBLE

        timeSelect = findViewById<SeekBar>(R.id.timeSelect)
        timeSelect.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (countDownStart) {
                    timer.cancel()
                    countDownStart = false
                }
                countDownTimeInMs = progress * 60000L

                // Update timer visually when sliding
                updateCountDownDisplay(countDownTimeInMs)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Is not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Is not implemented")
            }
        })

        pauseSelect = findViewById<SeekBar>(R.id.pauseSelect)
        pauseSelect.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                pauseCountDownInMs = progress * 60000L
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                print("Not implemented")
            }
        })

        repetitionCounter = findViewById<EditText>(R.id.repetition)

       startButton = findViewById<Button>(R.id.startCountdownButton)
       startButton.setOnClickListener(){
           startCountDown(it)
       }
        countdownDisplay = findViewById<TextView>(R.id.countDownView)

        // Enforce timer to show 15 minutes as start value on activity mount
        updateCountDownDisplay(countDownTimeInMs)
    }

    private fun startCountDown(v: View) {
        if (countDownStart) {
            timer.cancel()
            countDownStart = false
            Toast.makeText(this@MainActivity, "Stopping all timers", Toast.LENGTH_SHORT).show()
            return
        }

        timer = object : CountDownTimer(countDownTimeInMs, timeTicks) {
            override fun onFinish() {
                Toast.makeText(this@MainActivity, "Arbeidsøkt er ferdig", Toast.LENGTH_SHORT).show()
                countDownStart = false

                repetitoinAmount = repetitionCounter.text.toString().toInt()
                if (repetitoinAmount > 0) {
                    Toast.makeText(this@MainActivity, "Tid for en pause", Toast.LENGTH_SHORT).show()
                    startPauseTimer(v)
                    repetitoinAmount--
                    repetitionCounter.setText(repetitoinAmount.toString())
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                updateCountDownDisplay(millisUntilFinished)
            }
        }
        timer.start()
        countDownStart = true
    }

        private fun startPauseTimer(v: View) {
            if (pauseCountDownStart) {
                pauseTime.cancel()
                pauseCountDownStart = false
            }

            pauseTime = object : CountDownTimer(pauseCountDownInMs, timeTicks) {
                override fun onFinish() {
                    Toast.makeText(this@MainActivity, "Pausen er over.", Toast.LENGTH_SHORT).show()
                    pauseCountDownStart = false

                    repetitoinAmount = repetitionCounter.text.toString().toInt()
                    if (repetitoinAmount > 0) {
                        timer.start()
                    } else {
                        timer.cancel()
                    }

                    pauseText.visibility = View.INVISIBLE
                }

                override fun onTick(millisUntilFinished: Long) {
                    pauseText.visibility = View.VISIBLE
                    updateCountDownDisplay(millisUntilFinished)
                }
            }
            pauseTime.start()
            pauseCountDownStart = true
        }

        fun updateCountDownDisplay(timeInMs: Long){
            countdownDisplay.text = millisecondsToDescriptiveTime(timeInMs)

        }

}