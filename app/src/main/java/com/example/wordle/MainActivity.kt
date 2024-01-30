package com.example.wordle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import com.example.wordle.FourLetterWordList.getRandomFourLetterWord
import android.graphics.Color
import android.text.*
import android.text.style.ForegroundColorSpan


object FourLetterWordList {
    // List of most common 4 letter words from: https://7esl.com/4-letter-words/
    val fourLetterWords =
        "Area,Army,Baby,Back,Ball,Band,Bank,Base,Bill,Body,Book,Call,Card,Care,Case,Cash,City,Club,Cost,Date,Deal,Door,Duty,East,Edge,Face,Fact,Farm,Fear,File,Film,Fire,Firm,Fish,Food,Foot,Form,Fund,Game,Girl,Goal,Gold,Hair,Half,Hall,Hand,Head,Help,Hill,Home,Hope,Hour,Idea,Jack,John,Kind,King,Lack,Lady,Land,Life,Line,List,Look,Lord,Loss,Love,Mark,Mary,Mind,Miss,Move,Name,Need,News,Note,Page,Pain,Pair,Park,Part,Past,Path,Paul,Plan,Play,Post,Race,Rain,Rate,Rest,Rise,Risk,Road,Rock,Role,Room,Rule,Sale,Seat,Shop,Show,Side,Sign,Site,Size,Skin,Sort,Star,Step,Task,Team,Term,Test,Text,Time,Tour,Town,Tree,Turn,Type,Unit,User,View,Wall,Week,West,Wife,Will,Wind,Wine,Wood,Word,Work,Year,Bear,Beat,Blow,Burn,Call,Care,Cast,Come,Cook,Cope,Cost,Dare,Deal,Deny,Draw,Drop,Earn,Face,Fail,Fall,Fear,Feel,Fill,Find,Form,Gain,Give,Grow,Hang,Hate,Have,Head,Hear,Help,Hide,Hold,Hope,Hurt,Join,Jump,Keep,Kill,Know,Land,Last,Lead,Lend,Lift,Like,Link,Live,Look,Lose,Love,Make,Mark,Meet,Mind,Miss,Move,Must,Name,Need,Note,Open,Pass,Pick,Plan,Play,Pray,Pull,Push,Read,Rely,Rest,Ride,Ring,Rise,Risk,Roll,Rule,Save,Seek,Seem,Sell,Send,Shed,Show,Shut,Sign,Sing,Slip,Sort,Stay,Step,Stop,Suit,Take,Talk,Tell,Tend,Test,Turn,Vary,View,Vote,Wait,Wake,Walk,Want,Warn,Wash,Wear,Will,Wish,Work,Able,Back,Bare,Bass,Blue,Bold,Busy,Calm,Cold,Cool,Damp,Dark,Dead,Deaf,Dear,Deep,Dual,Dull,Dumb,Easy,Evil,Fair,Fast,Fine,Firm,Flat,Fond,Foul,Free,Full,Glad,Good,Grey,Grim,Half,Hard,Head,High,Holy,Huge,Just,Keen,Kind,Last,Late,Lazy,Like,Live,Lone,Long,Loud,Main,Male,Mass,Mean,Mere,Mild,Nazi,Near,Neat,Next,Nice,Okay,Only,Open,Oral,Pale,Past,Pink,Poor,Pure,Rare,Real,Rear,Rich,Rude,Safe,Same,Sick,Slim,Slow,Soft,Sole,Sore,Sure,Tall,Then,Thin,Tidy,Tiny,Tory,Ugly,Vain,Vast,Very,Vice,Warm,Wary,Weak,Wide,Wild,Wise,Zero,Ably,Afar,Anew,Away,Back,Dead,Deep,Down,Duly,Easy,Else,Even,Ever,Fair,Fast,Flat,Full,Good,Half,Hard,Here,High,Home,Idly,Just,Late,Like,Live,Long,Loud,Much,Near,Nice,Okay,Once,Only,Over,Part,Past,Real,Slow,Solo,Soon,Sure,That,Then,This,Thus,Very,When,Wide"
    // Returns a list of four letter words as a list
    fun getAllFourLetterWords(): List<String> {
        return fourLetterWords.split(",")
    }

    // Returns a random four letter word from the list in all caps
    fun getRandomFourLetterWord(): String {
        val allWords = getAllFourLetterWords()
        val randomNumber = (0..allWords.size).shuffled().last()
        return allWords[randomNumber].uppercase()
    }
}

class MainActivity<ConfettiView : View?> : AppCompatActivity() {
    // views
    private lateinit var button: Button
    private lateinit var textBox: EditText
    private lateinit var answer: TextView
    private lateinit var g1check: TextView
    private lateinit var g2: TextView
    private lateinit var g2check: TextView
    private lateinit var g3: TextView
    private lateinit var g3check: TextView
    private lateinit var guess1: TextView
    private lateinit var guess1check: TextView
    private lateinit var guess2: TextView
    private lateinit var guess2check: TextView
    private lateinit var guess3: TextView
    private lateinit var guess3check: TextView
    private lateinit var star: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById<Button>(R.id.button)
        textBox = findViewById<EditText>(R.id.textBox)
        answer = findViewById<EditText>(R.id.answer)
        g1check = findViewById<EditText>(R.id.g1check)
        g2 = findViewById<EditText>(R.id.g2)
        g2check = findViewById<EditText>(R.id.g2check)
        g3 = findViewById<EditText>(R.id.g3)
        g3check = findViewById<EditText>(R.id.g3check)
        guess1 = findViewById<EditText>(R.id.guess1)
        guess1check = findViewById<EditText>(R.id.guess1check)
        guess2 = findViewById<EditText>(R.id.guess2)
        guess2check = findViewById<EditText>(R.id.guess2check)
        guess3 = findViewById<EditText>(R.id.guess3)
        guess3check = findViewById<EditText>(R.id.guess3check)
        star = findViewById<ImageView>(R.id.star)




        var word = getRandomFourLetterWord()
        answer.text = word
        var guessCount = 0

        button.setOnClickListener {
            val enteredText = textBox.text.toString()
            if (enteredText == "") {
                // reset action
                word = getRandomFourLetterWord()
                answer.text = word
                guessCount = 0
                textBox.visibility = View.VISIBLE
                setVisibility(0)
            } else {
                // submit action
                if (enteredText.length == 4 && enteredText.all { it.isLetter() }) {
                    guessCount++
                    textBox.text.clear()
                    if (guessCount <= 3) {
                        setVisibility(guessCount)

                        // set correctly guessed events here
                        if (enteredText.uppercase() == word.uppercase()) {
                            answer.visibility = View.VISIBLE
                            star.visibility = View.VISIBLE
                            Log.v("correctly guessed!", "correctly guessed!")
                        }
                        val viewId =
                            resources.getIdentifier("guess${guessCount}", "id", packageName)
                        val view1 = findViewById<TextView>(viewId)
                        view1.text = enteredText.uppercase()

                        val viewId2 =
                            resources.getIdentifier("guess${guessCount}check", "id", packageName)
                        val view2 = findViewById<TextView>(viewId2)
                        val result = checkGuess(enteredText.uppercase(), word)
                        view2.setText(result, TextView.BufferType.SPANNABLE)
                        if (guessCount == 3) {
                            textBox.visibility = View.INVISIBLE
                        }
                    }
                }

            }
        }

        textBox.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable?) {
                // Update button text when text is change
                val enteredText = textBox.text.toString()
                if (enteredText.isBlank()) {
                    button.text = "Reset"
                } else {
                    button.text = "Submit"
                }
            }
        })
    }




    private fun setVisibility(x: Int) {
        /*
        0 => everything but g1 invisible
        x => guessX + gXcheck + guessXcheck + g(X+1) visible
         */
        if (x == 0) {
            g1check.visibility = View.INVISIBLE
            g2.visibility = View.INVISIBLE
            g2check.visibility = View.INVISIBLE
            g3.visibility = View.INVISIBLE
            g3check.visibility = View.INVISIBLE
            guess1.visibility = View.INVISIBLE
            guess1check.visibility = View.INVISIBLE
            guess2.visibility = View.INVISIBLE
            guess2check.visibility = View.INVISIBLE
            guess3.visibility = View.INVISIBLE
            guess3check.visibility = View.INVISIBLE
            answer.visibility = View.INVISIBLE
            star.visibility = View.INVISIBLE
        } else {
            val viewId = resources.getIdentifier("guess$x", "id", packageName)
            val view1 = findViewById<TextView>(viewId)
            view1.visibility = View.VISIBLE

            val viewId2 = resources.getIdentifier("g${x}check", "id", packageName)
            val view2 = findViewById<TextView>(viewId2)
            view2.visibility = View.VISIBLE

            val viewId3 = resources.getIdentifier("guess${x}check", "id", packageName)
            val view3 = findViewById<TextView>(viewId3)
            view3.visibility = View.VISIBLE

            if (x != 3) {
                val viewId4 = resources.getIdentifier("g${x + 1}", "id", packageName)
                val view4 = findViewById<TextView>(viewId4)
                view4.visibility = View.VISIBLE
            }
            else {
                answer.visibility = View.VISIBLE
            }
        }
    }

    private fun checkGuess(guess: String, answer: String): SpannableStringBuilder {
        val result = SpannableStringBuilder()
        for (i in 0..3) {
            if (guess[i] == answer[i]) {
                result.append(guess.substring(i,i+1))
                result.setSpan(ForegroundColorSpan(Color.GREEN), result.length - 1, result.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else if (guess[i] in answer) {
                result.append(guess.substring(i,i+1))
                result.setSpan(ForegroundColorSpan(Color.RED), result.length - 1, result.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
            else {
                result.append(guess.substring(i,i+1))
                result.setSpan(ForegroundColorSpan(Color.DKGRAY), result.length - 1, result.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return result
    }



}