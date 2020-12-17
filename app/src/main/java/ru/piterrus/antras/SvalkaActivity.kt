package ru.piterrus.antras

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import ru.piterrus.antras.DataClasses.Course
import ru.piterrus.antras.DataClasses.Education
import ru.piterrus.antras.DataClasses.Experience
import ru.piterrus.antras.DataClasses.Language
import ru.yandex.money.android.sdk.*
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.math.BigDecimal
import java.net.URL
import java.util.*
import kotlin.collections.HashSet

class SvalkaActivity : AppCompatActivity() {

    lateinit var buttonHttp: Button
    lateinit var getPdfButton:Button
    lateinit var goToYandex: Button
    lateinit var photoLoad: Button
    lateinit var sendPhotoToServer: Button
    lateinit var imageView: ImageView
    lateinit var img: Bitmap
    var selectedImage: String? = null
    lateinit var file: File
    var imageName: String? = null
    var myPaymentToken: String? = null
    var isPaidUser = false

    lateinit var paymentTimer: Timer
    lateinit var paymentTimerTask: PaymentTimerTask


    lateinit var pdfLayout: LinearLayout
    lateinit var buttonsLayout: ConstraintLayout

    lateinit var tokenTextView: TextView

    var arrayOfPhones = JSONArray()
    var arrayOfCourses = JSONArray()
    var arrayOfEducations = JSONArray()
    var arrayOfExperiences = JSONArray()
    var arrayOfLanguages = JSONArray()

    val JSON = MediaType.parse("application/json; charset=utf-8")

    var clientId = ""
    var resumeId = ""
    var paymentId = ""
    var confirmationUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


    }

    fun timeToStartCheckout() {
        val paymentTypes = HashSet<PaymentMethodType>()
        paymentTypes.add(PaymentMethodType.BANK_CARD)
        val intent = Checkout.createTokenizeIntent(this, paymentParameters =  PaymentParameters(
            Amount(BigDecimal(119), Currency.getInstance("RUB")),
            "Резюме",
            "резюме",
            "test_Njk2NDcxsLVka4HQSJq9FjWDrNxLO9xGROp7EMwG0zA",
            "696471",
            SavePaymentMethod.OFF,
            paymentMethodTypes = paymentTypes
        ))
        startActivityForResult(intent, 111)
    }

    override fun startActivityForResult(intent: Intent?, requestCode: Int) {
        super.startActivityForResult(intent, requestCode)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.v("requestCode", requestCode.toString())
        Log.v("resultCode", resultCode.toString())
        if (requestCode == 111) {
            if (resultCode == RESULT_OK) {
                // successful tokenization
                val result = Checkout.createTokenizationResult(data!!)
                runOnUiThread {
                    Toast.makeText(this, result.paymentToken, Toast.LENGTH_LONG).show()
                    tokenTextView.text = result.paymentToken
                }
                Log.v("ResultCODE", "RESULT_OK")
                Log.v("Result", result.toString())
                myPaymentToken = result.paymentToken
                sendPay()
            }
            if(resultCode == RESULT_CANCELED){
                // user canceled tokenization
                Log.v("Result", "RESULT_CANCELED")
            }
        }

        if (requestCode == 222) {
            val selectedImage = data?.data
            Log.v("Path", selectedImage?.lastPathSegment!!)
            file = File(selectedImage.lastPathSegment!!)
            val m = selectedImage.lastPathSegment!!.split("/")
            imageName = m.last()
            /*val b = BitmapFactory.decodeFile(selectedImage.lastPathSegment!!)
            val width: Int = b.getWidth()
            val height: Int = b.getHeight()
            Log.v("width", width.toString())
            Log.v("height", height.toString())
            val scaleWidth = 600F
            val scaleHeight = 600F
            // CREATE A MATRIX FOR THE MANIPULATION
            // CREATE A MATRIX FOR THE MANIPULATION
            val matrix = Matrix()
            // RESIZE THE BIT MAP
            // RESIZE THE BIT MAP
            matrix.postScale(scaleWidth, scaleHeight)

            // "RECREATE" THE NEW BITMAP

            // "RECREATE" THE NEW BITMAP
            val resizedBitmap = Bitmap.createBitmap(b, 0, 0, width, height, matrix, false)
            b.recycle()

            imageView.setImageBitmap(resizedBitmap)*/
            /*val lp = ConstraintLayout.LayoutParams(400,400)
            imageView.layoutParams = lp
            imageView.setBackgroundColor(Color.RED)
            imageView.scaleType = ImageView.ScaleType.FIT_XY*/
        }
    }

    fun sendPay(){
        try{
            val url = URL("https://mlab.antras.ru/api/payment/init?clientId=QEcNSN4HgvSWQdRaZPT89OeDtZyHk05I&resumeId=vLgwqn6E&provider=YandexKassa&agreement=true&paymentToken=$myPaymentToken")
            val okHttpClient = OkHttpClient()
            val body = FormBody.Builder().build()
            Log.v("body", body.toString())
            val request: Request = Request.Builder().url(url).addHeader("x-mobile", "true").post(body).build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }
                override fun onResponse(call: Call?, response: Response) {
                    val json = response.body()?.string()
                    if(JSONObject(json).has("paymentId")){
                        paymentId = JSONObject(json).get("paymentId").toString()
                        confirmationUrl = JSONObject(json).getJSONObject("confirmation").get("confirmation_url").toString()
                        startTimer()
                        sendConfirmationUrl()
                    }
                }
            })
        } catch (e: IOException) {
            Log.v("Error", e.message!!)
            return
        }
    }

    fun sendConfirmationUrl(){
        val intent3d = Checkout.create3dsIntent(this, confirmationUrl)
        startActivityForResult(intent3d, 333)
    }

    fun startTimer(){
        paymentTimer = Timer()
        paymentTimerTask = PaymentTimerTask()
        paymentTimer.schedule(paymentTimerTask, 0,1000L)
    }

    fun checkPay(){
        try{
            val url = URL("https://mlab.antras.ru/api/payment/status?clientId=QEcNSN4HgvSWQdRaZPT89OeDtZyHk05I&resumeId=vLgwqn6E&paymentId=$paymentId")
            val okHttpClient = OkHttpClient()
            val request: Request = Request.Builder().url(url).addHeader("x-mobile", "true").build()
            okHttpClient.newCall(request).enqueue(object: Callback {
                override fun onFailure(call: Call?, e: IOException?) {

                }
                override fun onResponse(call: Call?, response: Response) {
                    val json = response.body()?.string()
                    Log.v("jsonCheck", json)
                }
            })
        } catch (e: IOException) {
            Log.v("Error", e.message!!)
            return
        }
    }


    inner class PaymentTimerTask: TimerTask(){
        override fun run() {
            checkPay()
            if(isPaidUser){
                paymentTimer.cancel()
            }
        }

    }

    /*override fun onClick(v: View?) {
        when(v!!.id){
            R.id.goToYandex -> {
                timeToStartCheckout()
            }
            R.id.buttonHttp -> {
                for (i in 0..2){
                    val newPhone = "8911774697$i"
                    arrayOfPhones.put(i, newPhone)
                }
                for (j in 0..1){
                    var title = ""
                    if(j == 0){
                        title = "рекламе"
                    }
                    if(j == 1){
                        title = "боксу"
                    }
                    val newCourse = Course("1$j месяцев", 2010 + j, "Образование Ляляля", "Специалист по $title")
                    arrayOfCourses.put(j, JSONObject()
                        .put("duration", newCourse.duration)
                        .put("finished", newCourse.finished)
                        .put("institution", newCourse.institution)
                        .put("title", newCourse.title))
                }
                for (j in 0..1){
                    var title = ""
                    if(j == 0){
                        title = "рекламе"
                    }
                    if(j == 1){
                        title = "боксу"
                    }
                    val newEducation = Education(2010 + j, "ЛЭТИ", "higher", "Специалист по $title", "Электроника")
                    arrayOfEducations.put(j, JSONObject()
                        .put("finished", newEducation.finished)
                        .put("institution", newEducation.institution)
                        .put("level", newEducation.level)
                        .put("qualification", newEducation.qualification)
                        .put("specialization", newEducation.specialization))
                }

                for (j in 0..1){
                    var position = ""
                    if(j == 0){
                        position = "Инженер-Испытатель"
                    }
                    if(j == 1){
                        position = "Инженер-Программист"
                    }
                    val newExperience = Experience("01.201$j", "12.201$j", "Элеста", position, "К черту детади", false)
                    arrayOfExperiences.put(j, JSONObject()
                        .put("start", newExperience.start)
                        .put("end", newExperience.end)
                        .put("company", newExperience.company)
                        .put("position", newExperience.position)
                        .put("detail", newExperience.detail)
                        .put("tillnoew", newExperience.tillNow))
                }
                for (j in 0..1){
                    var int = 0
                    var title = ""
                    if(j == 0){
                        int = 6
                        title = "Английский"
                    }
                    if(j == 1){
                        int = 2
                        title = "Немецкий"
                    }
                    val newLanguage = Language(int, title)
                    arrayOfLanguages.put(j, JSONObject()
                        .put("level", newLanguage.level)
                        .put("title", newLanguage.title))
                }
                val json = JSONObject()
                    .put("clientId", "QEcNSN4HgvSWQdRaZPT89OeDtZyHk05I")
                    .put("resumeId", "vLgwqn6E")
                    .put("values", JSONObject()
                        .put("agreement", true)
                        .put("army", false)
                        .put("armyComment", "")
                        .put("additionalContact","")
                        .put("children", false)
                        .put("citizenShip", "Российская Федерация")
                        .put("city","г. Москва")
                        .put("computerSkills","Печать, сканирование, копирование документов, Интернет, Электронная почта, Microsoft Word, Microsoft Excel, Microsoft Power Point")
                        .put("coverLetter", "")
                        .put("driver", true)
                        .put("driverCat", "A, B")
                        .put("email","user@antras.com")
                        .put("employment", "full")
                        .put("family", "female-married")
                        .put("firstName", "Настасья")
                        .put("gender", "female")
                        .put("hobbies", "Читаю женские журналы, увлекаюсь модой, посещаю фитнес-клуб, хожу на курсы Французкого языка.")
                        .put("lastName", "Иванова")
                        .put("middleName", "Викторовна")
                        .put("personalQualities", "Поставленная речь, развитые дипломатические способности, опрятный и презентабельный вид, внимательность,  ответственность и пунктуальность.")
                        .put("position", "Администратор")
                        .put("relocation",false)
                        .put("salary", "100 000")
                        .put("schedule", "fulltime")
                        .put("trip", false)
                        .put("birthday", "10.09.1990")
                        .put("photo", selectedImage)
                        .put("phones", arrayOfPhones)
                        .put("courses", arrayOfCourses)
                        .put("education", arrayOfEducations)
                        .put("experience",  arrayOfExperiences)
                        .put("languages", arrayOfLanguages)).toString()
                try{
                    //Log.v("IMAGETOSERVER", selectedImage)
                    val url = URL("https://mlab.antras.ru/api/resume/data")
                    val okHttpClient = OkHttpClient()
                    val body = RequestBody.create(JSON, json)
                    Log.v("JSON", json)

                    val request: Request = Request.Builder().url(url).post(body).addHeader("x-mobile", "true").build()
                    okHttpClient.newCall(request).enqueue(object: Callback {
                        override fun onFailure(call: Call?, e: IOException?) {

                        }
                        override fun onResponse(call: Call?, response: Response) {
                            val json = response.body()?.string()
                            Log.v("JSON", json)
                            clientId = (JSONObject(json).get("clientId")).toString()
                            resumeId = (JSONObject(json).get("resumeId")).toString()
                            Log.v("clientId", clientId)
                            Log.v("resumeId", resumeId)
                        }
                    })
                } catch (e: IOException) {
                    Log.v("Error", e.message!!)
                    return
                }
            }

            R.id.sendPhotoToServer -> {
                try{
                val url = URL("https://mlab.antras.ru/api/resume/photo")
                val okHttpClient = OkHttpClient()
                val body = MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("file", imageName, RequestBody.create(MediaType.parse("image/jpeg"), file))
                    .build()
                val request: Request = Request.Builder()
                    .url(url)
                    .post(body)
                    .addHeader("x-mobile", "true")
                    .build()
                okHttpClient.newCall(request).enqueue(object: Callback {
                    override fun onFailure(call: Call?, e: IOException?) {
                        Log.v("JSON", "HUI")
                    }
                    override fun onResponse(call: Call?, response: Response) {
                        val json = response.body()?.string()
                        Log.v("JSON", json)
                        selectedImage = JSONObject(json).get("imageUrl").toString()
                    }
                })
            } catch (e: IOException) {
            Log.v("Error", e.message!!)
            return
        }
            }

            R.id.getPdfButton -> {
                try{
                    val url = URL("https://mlab.antras.ru/api/pdf/generate/default?clientId=$clientId&resumeId=$resumeId")
                    val okHttpClient = OkHttpClient()

                    val request: Request = Request.Builder().url(url).addHeader("x-mobile", "true").build()
                    okHttpClient.newCall(request).enqueue(object: Callback {
                        override fun onFailure(call: Call?, e: IOException?) {

                        }
                        override fun onResponse(call: Call?, response: Response) {
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(baseContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                    ActivityCompat.requestPermissions(this@SvalkaActivity, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                                }
                            }
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                if (ContextCompat.checkSelfPermission(baseContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                                    val json = response.body()?.bytes()
                                    Log.v("json", json.toString())
                                    val file = File("storage/emulated/0/Download","My22.pdf")
                                    val pWriter = FileOutputStream(file, false)
                                    pWriter.write(json!!)
                                    pWriter.close()
                                }
                            }
                        }
                    })
                } catch (e: IOException) {
                    Log.v("Error", e.message!!)
                    return
                }

                pdfLayout.visibility = View.VISIBLE
                buttonsLayout.visibility = View.GONE
                val file = File("storage/emulated/0/Download","My22.pdf")
                val pdfView = findViewById<PDFView>(R.id.pdfView)
                pdfView.fromFile(file)
                    .pages(0, 2, 1, 3, 3, 3) // all pages are displayed by default
                    .enableSwipe(true) // allows to block changing pages using swipe
                    .swipeHorizontal(false)
                    .enableDoubletap(true)
                    .defaultPage(0)
                    // allows to draw something on the current page, usually visible in the middle of the screen
                    .enableAnnotationRendering(false) // render annotations (such as comments, colors or forms)
                    .password(null)
                    .scrollHandle(null)
                    .enableAntialiasing(true) // improve rendering a little bit on low-res screens
                    // spacing between pages in dp. To define spacing color, set view background
                    .spacing(0)
                    .pageFitPolicy(FitPolicy.WIDTH) // mode to fit pages in the view
                    .load();
            }*/
            //R.id.photoLoad -> {
            //    val intent = Intent()
            //    intent.setType("image/*")
            //    intent.setAction(Intent.ACTION_PICK)
            //    startActivityForResult(Intent.createChooser(intent, "Select Picture"), 222)
           // }
        //}
    //}


    /*override fun onBackPressed() {
        if(buttonsLayout.visibility == View.VISIBLE){
            super.onBackPressed()
        }
        pdfLayout.visibility = View.GONE
        buttonsLayout.visibility = View.VISIBLE
    }*/
}
