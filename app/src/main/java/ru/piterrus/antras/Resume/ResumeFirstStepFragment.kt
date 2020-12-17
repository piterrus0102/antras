package ru.piterrus.antras.Resume

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.Typeface
import android.os.Build
import android.os.Bundle
import android.text.Spannable
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.barteksc.pdfviewer.PDFView
import com.github.barteksc.pdfviewer.util.FitPolicy
import com.google.gson.JsonArray
import okhttp3.*
import org.json.JSONArray
import org.json.JSONObject
import org.w3c.dom.Text
import ru.piterrus.antras.DataClasses.*
import ru.piterrus.antras.R
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.net.URL

class ResumeFirstStepFragment : Fragment(), View.OnClickListener {

    lateinit var fillinTextView: TextView
    lateinit var exampleSpinner: Spinner
    lateinit var textView110: TextView
    lateinit var fillinFieldsButton: Button
    lateinit var photoImageView: ImageView
    lateinit var uploadPhotoButton: Button


    lateinit var surnameTextView: TextView
    lateinit var nameTextView: TextView
    lateinit var patronymicTextView: TextView
    lateinit var wantedPositionTextView: TextView
    lateinit var expectedSalaryTextView: TextView
    lateinit var workScheduleSpinner: Spinner
    lateinit var employmentSpinner: Spinner
    lateinit var readyToMoveSwitch: Switch
    lateinit var businessTripSwitch: Switch

    lateinit var phoneTextView: TextView
    lateinit var mailTextView: TextView
    lateinit var networkProfileTextView: TextView

    lateinit var citizenShipTextView: TextView
    lateinit var residingPlaceTextView: TextView
    lateinit var birthdayTextView: TextView
    lateinit var genderSpinner: Spinner
    lateinit var maritalStatusSpinner: Spinner
    lateinit var childrenSwitch: Switch
    lateinit var driverLicenseCheckbox: CheckBox
    lateinit var driverLicenseCategoryTextView: TextView
    lateinit var armyCheckbox: CheckBox
    lateinit var armyInfoTextView: TextView

    lateinit var startWorkTextView: TextView
    lateinit var endWorkTextView: TextView
    lateinit var currentWorkTimeSwitch: Switch
    lateinit var organizationNameTextView: TextView
    lateinit var positionTextView: TextView
    lateinit var dutiesTextView: TextView
    lateinit var removeWorkPointButton: Button

    lateinit var educationLevelSpinner: Spinner
    lateinit var educationalInstitutionTextView: TextView
    lateinit var facultyTextView: TextView
    lateinit var qualificationTextView: TextView
    lateinit var endDateOfEducationTextView: TextView
    lateinit var removeEducationPointButton: Button

    lateinit var courseNameTextView: TextView
    lateinit var courseBuildingTextView: TextView
    lateinit var timeDistanceTextView: TextView
    lateinit var endDateOfCourseTextView: TextView
    lateinit var removeCoursePointButton: Button

    lateinit var nameOfLanguageTextView: TextView
    lateinit var languageLevelSeekBar: SeekBar
    lateinit var removeLanguagePointButton: Button

    lateinit var programmsTextView: TextView
    lateinit var hobbiesTextView: TextView
    lateinit var personalQualityTextView: TextView
    lateinit var additionalInfoTextView: TextView

    lateinit var personalDataAgreementCheckbox: CheckBox
    lateinit var clearFormButton: Button
    lateinit var goToSubviewButton: Button

    var examplesArray = ArrayList<JSONExamples>()

    var TAG = this.javaClass.canonicalName


    var genderArray = arrayOf("Не указано", "Мужской", "Женский")

    var testArray = arrayOf("Администратор", "Менеджер", "Рабочий")

    var arrayOfEmployment = arrayOf("Полная", "Частичная", "Проектная работа", "Стажировка", "Волонтерство")

    var arrayOfSchedule = arrayOf("Полный день", "Сменный график", "Гибкий график", "Удаленная работа", "Вахтовый метод")

    var arrayOfMaritalStatus = arrayOf("Не указано", "Холост", "Женат", "Не замужем", "Замужем")

    var arrayOfEducationLevels = arrayOf("Доктор наук", "Кандидат наук", "Высшее", "Неоконченное высшее", "Среднее специальное", "Среднее", "Не указано")

    var selectedGender = ""
    var selectedEmployment = ""
    var selectedMaritalStatus = ""
    var selectedWorkSchedule = ""
    var selectedEducationLevel = ""

    var arrayOfCourses = JSONArray()
    var arrayOfEducations = JSONArray()
    var arrayOfExperiences = JSONArray()
    var arrayOfLanguages = JSONArray()

    val JSON = MediaType.parse("application/json; charset=utf-8")

    var clientId = ""
    var resumeId = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_resume_first_step, null)

        fillinFieldsButton = v.findViewById(R.id.fillinFieldsButton)
        photoImageView = v.findViewById(R.id.photoImageView)
        uploadPhotoButton = v.findViewById(R.id.uploadPhotoButton)
        uploadPhotoButton.setOnClickListener(this)
        fillinFieldsButton.setOnClickListener(this)


        surnameTextView = v.findViewById(R.id.surnameTextView)
        nameTextView = v.findViewById(R.id.nameTextView)
        patronymicTextView = v.findViewById(R.id.patronymicTextView)
        wantedPositionTextView = v.findViewById(R.id.wantedPositionTextView)
        expectedSalaryTextView = v.findViewById(R.id.expectedSalaryTextView)
        workScheduleSpinner = v.findViewById(R.id.workScheduleSpinner)
        employmentSpinner = v.findViewById(R.id.employmentSpinner)
        readyToMoveSwitch = v.findViewById(R.id.readyToMoveSwitch)
        businessTripSwitch = v.findViewById(R.id.businessTripSwitch)

        phoneTextView = v.findViewById(R.id.phoneTextView)
        mailTextView = v.findViewById(R.id.mailTextView)
        networkProfileTextView = v.findViewById(R.id.networkProfileTextView)

        citizenShipTextView = v.findViewById(R.id.citizenShipTextView)
        residingPlaceTextView = v.findViewById(R.id.residingPlaceTextView)
        birthdayTextView = v.findViewById(R.id.birthdayTextView)
        genderSpinner = v.findViewById(R.id.genderSpinner)
        maritalStatusSpinner = v.findViewById(R.id.maritalStatusSpinner)
        childrenSwitch = v.findViewById(R.id.childrenSwitch)
        driverLicenseCheckbox = v.findViewById(R.id.driverLicenseCheckbox)
        driverLicenseCategoryTextView = v.findViewById(R.id.driverLicenseCategoryTextView)
        armyCheckbox = v.findViewById(R.id.armyCheckbox)
        armyInfoTextView = v.findViewById(R.id.armyInfoTextView)

        startWorkTextView = v.findViewById(R.id.startWorkTextView)
        endWorkTextView = v.findViewById(R.id.endWorkTextView)
        currentWorkTimeSwitch = v.findViewById(R.id.currentWorkTimeSwitch)
        organizationNameTextView = v.findViewById(R.id.organizationNameTextView)
        positionTextView = v.findViewById(R.id.positionTextView)
        dutiesTextView = v.findViewById(R.id.dutiesTextView)
        removeWorkPointButton = v.findViewById(R.id.removeWorkPointButton)
        removeWorkPointButton.setOnClickListener(this)

        educationLevelSpinner = v.findViewById(R.id.educationLevelSpinner)
        educationalInstitutionTextView = v.findViewById(R.id.educationalInstitutionTextView)
        facultyTextView = v.findViewById(R.id.facultyTextView)
        qualificationTextView = v.findViewById(R.id.qualificationTextView)
        endDateOfEducationTextView = v.findViewById(R.id.endDateOfEducationTextView)
        removeEducationPointButton = v.findViewById(R.id.removeEducationPointButton)
        removeEducationPointButton.setOnClickListener(this)

        courseNameTextView = v.findViewById(R.id.courseNameTextView)
        courseBuildingTextView = v.findViewById(R.id.courseBuildingTextView)
        timeDistanceTextView = v.findViewById(R.id.timeDistanceTextView)
        endDateOfCourseTextView = v.findViewById(R.id.endDateOfCourseTextView)
        removeCoursePointButton = v.findViewById(R.id.removeCoursePointButton)
        removeCoursePointButton.setOnClickListener(this)

        nameOfLanguageTextView = v.findViewById(R.id.nameOfLanguageTextView)
        languageLevelSeekBar = v.findViewById(R.id.languageLevelSeekBar)
        removeLanguagePointButton = v.findViewById(R.id.removeLanguagePointButton)
        removeLanguagePointButton.setOnClickListener(this)


        programmsTextView = v.findViewById(R.id.programmsTextView)
        hobbiesTextView = v.findViewById(R.id.hobbiesTextView)
        personalQualityTextView = v.findViewById(R.id.personalQualityTextView)
        additionalInfoTextView = v.findViewById(R.id.additionalInfoTextView)

        personalDataAgreementCheckbox = v.findViewById(R.id.personalDataAgreementCheckbox)
        clearFormButton = v.findViewById(R.id.clearFormButton)
        goToSubviewButton = v.findViewById(R.id.goToSubviewButton)
        clearFormButton.setOnClickListener(this)
        goToSubviewButton.setOnClickListener(this)


        fillinTextView = v.findViewById(R.id.fillinTextView)
        exampleSpinner = v.findViewById(R.id.exampleSpinner)
        textView110 = v.findViewById(R.id.textView110)
        exampleSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, testArray)
        employmentSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, arrayOfEmployment)
        employmentSpinner.onItemSelectedListener = employmentSpinnerPoint
        genderSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, genderArray)
        genderSpinner.onItemSelectedListener = genderSpinnerPoint
        maritalStatusSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, arrayOfMaritalStatus)
        maritalStatusSpinner.onItemSelectedListener = maritalStatusSpinnerPoint
        workScheduleSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, arrayOfSchedule)
        workScheduleSpinner.onItemSelectedListener = workScheduleSpinnerPoint
        educationLevelSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, arrayOfEducationLevels)
        educationLevelSpinner.onItemSelectedListener = educationLevelSpinnerPoint
        val first = "   Для составления "
        val another = "резюме на работу по образцу "
        val next = "выберите пример резюме из списка и нажмите "
        val final = "Заполнить поля"
        fillinTextView.setText(first + another + next + final, TextView.BufferType.SPANNABLE)
        val s = fillinTextView.text as Spannable
        val start = first.length
        val end = start + another.length
        s.setSpan(StyleSpan(Typeface.BOLD), start, end, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val start2 = first.length + another.length + next.length
        val end2 = start2 + final.length
        s.setSpan(StyleSpan(Typeface.BOLD), start2, end2, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val first3 = "Даю согласие на обработку моих данных в соответствии с "
        val second3 = "политикой обработки данных."
        textView110.setText(first3 + second3, TextView.BufferType.SPANNABLE)
        val s2 = textView110.text as Spannable
        val start3 = first3.length
        val end3 = start3 + second3.length
        s2.setSpan(ForegroundColorSpan(Color.parseColor("#2C98F0")), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        val s3 = textView110.text as Spannable
        s3.setSpan(StyleSpan(Typeface.BOLD), start3, end3, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return v
    }

    override fun onClick(v: View) {
        when(v.id){
            R.id.fillinFieldsButton ->{
                val url = URL("https://antras.ru/examples/json/index.json")
                val okHttpClient = OkHttpClient()
                val request: Request = Request.Builder().url(url).addHeader("x-mobile", "true").build()
                okHttpClient.newCall(request).enqueue(object: Callback{
                    override fun onFailure(call: Call, e: IOException) {

                    }

                    override fun onResponse(call: Call, response: Response) {
                        val json = response.body()?.string()
                        val jsonArr = JSONArray(json)
                        for(i in 0..JSONArray(json).length()-1){
                            val value = jsonArr.getJSONObject(i).get("value").toString()
                            val label = jsonArr.getJSONObject(i).get("label").toString()
                            val jsonFile = jsonArr.getJSONObject(i).get("jsonFile").toString()
                            val pdfFile = jsonArr.getJSONObject(i).get("pdfFile").toString()
                            val imageFile = jsonArr.getJSONObject(i).get("imageFile").toString()
                            val example = JSONExamples(value, label, jsonFile, pdfFile, imageFile)
                            examplesArray.add(example)
                        }
                        
                    }

                })
            }
            R.id.uploadPhotoButton -> {

            }

            R.id.removeWorkPointButton -> {

            }

            R.id.removeEducationPointButton -> {

            }

            R.id.removeLanguagePointButton -> {

            }

            R.id.clearFormButton -> {

            }
            R.id.removeCoursePointButton -> {

            }

            R.id.goToSubviewButton -> {
                val agreement = {
                    if(personalDataAgreementCheckbox.isChecked) true
                    false
                }()
                val army = {
                    if(armyCheckbox.isChecked) true
                    false
                }()
                val children = {
                    if(childrenSwitch.isChecked) true
                    false
                }()
                val armyComment = armyInfoTextView.text
                val sitizenShip = citizenShipTextView.text
                val city = residingPlaceTextView.text
                val computerSkills = programmsTextView.text
                val coverLetter = additionalInfoTextView.text
                val driver = {
                    if(driverLicenseCheckbox.isChecked) true
                    false
                }()
                val driverCat = driverLicenseCategoryTextView.text
                val email = mailTextView.text
                val employmentJSON = {
                    if(selectedEmployment == "Полная") "full"
                    if(selectedEmployment == "Частичная") "parttime"
                    if(selectedEmployment == "Проектная") "project"
                    if(selectedEmployment == "Стажировка") "intership"
                    if(selectedEmployment == "Волонтерство") "volunteering"
                }()

                val relocation = {
                    if(readyToMoveSwitch.isChecked) true
                    false
                }()
                val trip = {
                    if(businessTripSwitch.isChecked) true
                    false
                }()
                val gender = {
                    if(selectedGender == "Не указано") "other"
                    if(selectedGender == "Мужской") "male"
                    if(selectedGender == "Женский") "female"
                }()
                val maritalStatus = {
                    if(selectedMaritalStatus == "Не указано") "other"
                    if(selectedMaritalStatus == "Холост") "male-single"
                    if(selectedMaritalStatus == "Женат") "male-married"
                    if(selectedMaritalStatus == "Не замужем") "female-single"
                    if(selectedMaritalStatus == "Замужем") "female-married"
                }()

                val endDate = endDateOfCourseTextView.text.toString()
                val intEndDate = endDate.toInt()
                val newCourse = Course(timeDistanceTextView.text.toString(), intEndDate, courseBuildingTextView.text.toString(), courseNameTextView.text.toString())
                arrayOfCourses.put(0, JSONObject()
                    .put("duration", newCourse.duration)
                    .put("finished", newCourse.finished)
                    .put("institution", newCourse.institution)
                    .put("title", newCourse.title))
                val level = {
                    if(selectedEducationLevel == "Доктор наук") "doctor"
                    if(selectedEducationLevel == "Кандидат наук") "candidate"
                    if(selectedEducationLevel == "Высшее") "higher"
                    if(selectedEducationLevel == "Неоконченное высшее") "unfinished_higher"
                    if(selectedEducationLevel == "Среднее специальное") "special-secondary"
                    if(selectedEducationLevel == "Среднее") "secondary"
                    if(selectedEducationLevel == "Не указано") "other"
                }()

                val schedule = {
                    if(selectedWorkSchedule == "Полный день") "fulltime"
                    if(selectedWorkSchedule == "Сменный график") "parttime"
                    if(selectedWorkSchedule == "Гибкий график") "parttime"
                    if(selectedWorkSchedule == "Удаленная работа") "remote"
                    if(selectedWorkSchedule == "Вахтовый метод") "rotational"
                }()
                val newEducation = Education(endDateOfEducationTextView.text.toString().toInt(),
                                            educationalInstitutionTextView.text.toString(),
                                            level.toString(),
                                            qualificationTextView.text.toString(),
                                            facultyTextView.toString())
                arrayOfEducations.put(0, JSONObject()
                    .put("finished", newEducation.finished)
                    .put("institution", newEducation.institution)
                    .put("level", newEducation.level)
                    .put("qualification", newEducation.qualification)
                    .put("specialization", newEducation.specialization))

                val stillNow: Boolean = {
                    if(currentWorkTimeSwitch.isChecked) true
                    false
                }()
                val newExperience = Experience(startWorkTextView.text.toString(),
                    endWorkTextView.text.toString(),
                    organizationNameTextView.text.toString(),
                    positionTextView.text.toString(),
                    dutiesTextView.text.toString(), stillNow)
                arrayOfExperiences.put(0, JSONObject()
                    .put("start", newExperience.start)
                    .put("end", newExperience.end)
                    .put("company", newExperience.company)
                    .put("position", newExperience.position)
                    .put("detail", newExperience.detail)
                    .put("tillnoew", newExperience.tillNow))

                val levelOfLanguage = languageLevelSeekBar.progress
                val newLanguage = Language(levelOfLanguage, nameOfLanguageTextView.text.toString())
                arrayOfLanguages.put(0, JSONObject()
                    .put("level", newLanguage.level)
                    .put("title", newLanguage.title))

                val json = JSONObject()
                    .put("values", JSONObject()
                        .put("agreement", agreement)
                        .put("army", army)
                        .put("armyComment", armyComment)
                        .put("additionalContact","")
                        .put("children", children)
                        .put("citizenShip", sitizenShip)
                        .put("city",city)
                        .put("computerSkills",computerSkills)
                        .put("coverLetter", coverLetter)
                        .put("driver", driver)
                        .put("driverCat", driverCat)
                        .put("email",email)
                        .put("employment", employmentJSON)
                        .put("family", maritalStatus)
                        .put("firstName", nameTextView.text.toString())
                        .put("gender", gender)
                        .put("hobbies", hobbiesTextView.text.toString())
                        .put("lastName", surnameTextView.text.toString())
                        .put("middleName", patronymicTextView.text.toString())
                        .put("personalQualities", personalQualityTextView.text.toString())
                        .put("position", positionTextView.text.toString())
                        .put("relocation",relocation)
                        .put("salary", expectedSalaryTextView.text.toString())
                        .put("schedule", schedule)
                        .put("trip", trip)
                        .put("birthday", birthdayTextView.text.toString())
                        .put("photo", "/upload-photos/2020-10/17/08-22-25.933-13d2fcfa8b94c20.r.jpg")
                        .put("phones", phoneTextView.text.toString())
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
                            try{
                                val url1 = URL("https://mlab.antras.ru/api/pdf/generate/default?clientId=$clientId&resumeId=$resumeId")
                                val okHttpClient1 = OkHttpClient()

                                val request1: Request = Request.Builder().url(url1).addHeader("x-mobile", "true").build()
                                okHttpClient1.newCall(request1).enqueue(object: Callback {
                                    override fun onFailure(call: Call?, e: IOException?) {

                                    }
                                    override fun onResponse(call: Call?, response: Response) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                                ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
                                            }
                                        }
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ContextCompat.checkSelfPermission(context!!, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
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
                            val ft = activity!!.supportFragmentManager.beginTransaction()
                            ft.replace(R.id.fragmentContainer, ResumeSecondStepFragment())
                            ft.addToBackStack(null)
                            ft.commit()
                        }
                    })
                } catch (e: IOException) {
                    Log.v("Error", e.message!!)
                    return
                }
            }
        }
    }


    var employmentSpinnerPoint = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedEmployment = parent?.getItemAtPosition(position) as String
        }

    }
    var genderSpinnerPoint = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedGender = parent?.getItemAtPosition(position) as String
            if(position == 0){
                arrayOfMaritalStatus = arrayOf("Не указано", "Холост", "Женат", "Не замужем", "Замужем")
            }
            if(position == 1){
                arrayOfMaritalStatus = arrayOf("Не указано", "Холост", "Женат")
            }
            if(position == 2){
                arrayOfMaritalStatus = arrayOf("Не указано", "Не замужем", "Замужем")
            }
            maritalStatusSpinner.adapter = ArrayAdapter(activity!!, android.R.layout.simple_spinner_dropdown_item, arrayOfMaritalStatus)
        }

    }

    var maritalStatusSpinnerPoint = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedMaritalStatus = parent?.getItemAtPosition(position) as String
        }

    }
    var workScheduleSpinnerPoint = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedWorkSchedule = parent?.getItemAtPosition(position) as String
        }

    }
    var educationLevelSpinnerPoint = object : AdapterView.OnItemSelectedListener{
        override fun onNothingSelected(parent: AdapterView<*>?) {
            //
        }

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            selectedEducationLevel = parent?.getItemAtPosition(position) as String
        }

    }
}
