package com.example.quiz_app_starter.model

data class Question(
    val category: String,
    val id: String,
    val correctAnswer: String,
    val answers: List<String>,
    val tags: List<String>,
    val question: String,
    val type: String,
    val difficulty: String,
    val regions: List<String>?,
    val isNiche: Boolean
)

fun getDummyQuestions(): List<Question> {
    return listOf(
        Question(
            category = "history",
            id = "622a1c3b7cc59eab6f95171f",
            correctAnswer = "Hadrian's Wall",
            answers = listOf("Hadrian's Wall", "Caesar's Wall", "Julius' Wall", "Octavian's Wall"),
            tags = listOf("the_romans", "scotland", "history"),
            question = "Which wall did the Romans build to keep out marauding Scots?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "science",
            id = "62443736746187c5e7be933c",
            correctAnswer = "Bleach",
            answers = listOf("Bleach", "Chloroform", "QuickSilver", "Caustic soda"),
            tags = listOf("science"),
            question = "What is the common name for sodium hypochlorite?",
            type = "text_choice",
            difficulty = "hard",
            regions = emptyList(),
            isNiche = false
        ),

        Question(
            category = "sport_and_leisure",
            id = "62399f12af96521963a08717",
            correctAnswer = "England",
            answers = listOf("England", "Brazil", "France", "Switzerland"),
            tags = listOf("sport"),
            question = "Which country hosted the 1966 FIFA World Cup?",
            type = "text_choice",
            difficulty = "hard",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "film_and_tv",
            id = "646140b74d46e537ca8cda13",
            correctAnswer = "Keyser Söze",
            answers = listOf("Keyser Söze", "Keyser Wilhelm", "Keyser Gundogan", "Keyser Rommel"),
            tags = listOf("film", "film_and_tv"),
            question = "In the movie \"The Usual Suspects\", who might have been the killer named Verbal Kint?",
            type = "text_choice",
            difficulty = "hard",
            regions = emptyList(),
            isNiche = false
        ),

        Question(
            category = "society_and_culture",
            id = "6262af614b176d54800e3dc2",
            correctAnswer = "Shaking My Head",
            answers = listOf("Shaking My Head", "Sometimes Men Hate", "Special Man, Him", "Soothe Me Honey"),
            tags = listOf("the_internet", "initials", "society_and_culture"),
            question = "What do people mean when type the letters 'SMH' in a message on the internet?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "film_and_tv",
            id = "63d9531f168979b94b2e4fa1",
            correctAnswer = "Bad Boys",
            answers = listOf("Bad Boys", "Men in Black", "Independence Day", "Wild Wild West"),
            tags = listOf("1990's", "film_and_tv", "film"),
            question = "What was the name of the popular '90s police film starring Will Smith and Martin Lawrence?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "society_and_culture",
            id = "64720cd93bbb58c31f411ccc",
            correctAnswer = "Reincarnation",
            answers = listOf("Reincarnation", "Immortality", "Purgatory", "Afterlife"),
            tags = listOf("philosophy", "religion", "society_and_culture"),
            question = "What is the term used for the belief that the soul is reborn in a new form after death?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "film_and_tv",
            id = "648889175dba6c873ef650df",
            correctAnswer = "Wayne Enterprises",
            answers = listOf("Wayne Enterprises", "Superhero Inc.", "Gotham Corp.", "The Dark Knight Co."),
            tags = listOf("comics", "dc", "batman", "film_and_tv", "film", "superheroes"),
            question = "What is the name of the fictional company owned by Batman?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "science",
            id = "622a1c377cc59eab6f950503",
            correctAnswer = "butterflies and moths",
            answers = listOf("butterflies and moths", "animal diseases", "the effect of light on chemicals", "the historical study of languages"),
            tags = listOf("words", "science"),
            question = "What is Lepidopterology the study of?",
            type = "text_choice",
            difficulty = "hard",
            regions = emptyList(),
            isNiche = false
        ),
        Question(
            category = "music",
            id = "64746ab65a4c31088dd9cfc6",
            correctAnswer = "UB40",
            answers = listOf("UB40", "Red Hot Chili Peppers", "The Police", "Radiohead"),
            tags = listOf("bands", "songs", "music"),
            question = "What band's rendition of 'Red Red Wine' topped the charts in 1988?",
            type = "text_choice",
            difficulty = "medium",
            regions = emptyList(),
            isNiche = false
        )
    )
}