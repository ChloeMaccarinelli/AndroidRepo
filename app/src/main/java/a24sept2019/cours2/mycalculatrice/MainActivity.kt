/**
 * Code par Maccarinelli Chloé & Fernandez Arnaud
 *
 * Dans le cadre du TP1 de développement d'application Android
 * MBDS Sophia Antipolis
 * Promotion 2019 / 2020
 */


package a24sept2019.cours2.mycalculatrice

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import net.java.dev.eval.*
import android.text.method.ScrollingMovementMethod



/**
 *
 * class MainActivity()
 *
 * Classe principale de l'application, gère les différents évémenements déclanchés par l'application
 *
 */
class MainActivity : AppCompatActivity() {

    var stringNumber = ""
    var res = 0f
    val signes = listOf("/", "*", "+", "-", ".", "=")
    var lastChar: String = " "
    var operationText : TextView? = null
    lateinit var resultatText: TextView
    var historique : String = ""

    /**
     * onCreate(Bundle?)
     *
     * Est appelé lors de l'instanciation de la classe
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // On récupère les zones de texte dans lesquelles on va afficher les valeurs
        operationText = findViewById(R.id.operationText)
        resultatText = findViewById(R.id.resultatText)
    }

    /**
     * creerResultat()
     *
     * Créer l'objet Calculatrice qui va prendre en paramètre la string finale pour le calcul
     * On récupère dans la variable res le résultat de l'opération, et on reset la string de calcul
     *
     */
    fun creerResultat() {

        // Si l'utilisateur a tapé un signe en dernier, et qu'il tape un autre signe, on le bloque
        if(signes.contains(this.stringNumber.last().toString())) {
            this.stringNumber = this.stringNumber.dropLast(1)
        }

        val cal = Calculatrice(stringACalculer = this.stringNumber)

        // On va vérifier si l'opération n'a pas lever une erreur
        // Si c'est le cas, on el notifie par un texte lui disant qu'il y a une erreur
        if(cal.isError().not()) {
            res = cal.getResultat()
            resultatText.setText(res.toString())
        } else {
            resultatText.setText("Erreur dans le calcul")
        }
        updateHistorique()

    }

    fun updateHistorique() {

        var historiqueText : TextView = findViewById(R.id.historiqueText)
        historiqueText.setMovementMethod(ScrollingMovementMethod())


        var stringToHistorize = ""

        stringToHistorize += res
        stringToHistorize += "\n"

        this.historique += stringToHistorize

        historiqueText!!.setText(this.historique)
    }

    /**
     *
     * setListeners(View)
     *
     * Méthode appelée par les boutons de la calculatrice, va gérer les différentes opérations
     * demandées par l'utilisateur
     */
    fun setListeners(v: View) {

        if (v is Button) {

                lastChar = v.tag.toString()

                when {
                    // On interdit de mettre un signe contenu dans la liste des signes d'opérations ...
                    this.stringNumber.isEmpty() && signes.contains(lastChar) -> println("Caractère interdit")
                    // Mais si c'est un moins, on l'accepte dans le cas ou l'utilisateur veut mettre un négatif en premier
                    this.stringNumber.isEmpty() && lastChar == "-" -> this.stringNumber += lastChar
                    lastChar.equals("=")  -> creerResultat()
                    signes.contains(lastChar) && signes.contains(this.stringNumber.last().toString()) -> println("Caractère interdit")
                    lastChar.equals("C") -> this.stringNumber = ""
                    lastChar.equals("S") -> this.suppr()

                    else -> this.stringNumber += lastChar
                }
                operationText!!.setText(this.stringNumber)
        }
    }


    /**
     *
     * suppr()
     *
     * Supprime le dernier chiffre ou le dernier opérateur saisi par l'utilisateur
     */
    fun suppr() {
        this.stringNumber = this.stringNumber.dropLast(1)
        this.operationText!!.setText(this.stringNumber);
    }

}

/**
 *
 * class Calculatrice(String)
 *
 * Classe qui prend en paramètre la chaine d'opérations à calculer et qui garde le résultat
 * dans une variable
 */
class Calculatrice(val stringACalculer: String) {


    var res: Float = 0f
    var finalString = ""
    var exp: Expression? = null
    var error : Boolean = false

    init {
        this.finalString = stringACalculer

        try {
            exp = Expression(this.finalString)
        } catch (e : java.lang.Exception) {
            println("Erreur dans le calcul")
        }
        this.calcul()
    }

    /**
     *
     * calcul()
     *
     * Calcul l'opération récupérée lors de l'init de la classe et affecte le résultat dans la
     * variable res
     *
     */
    fun calcul() {
        try {
            res = exp!!.eval().toFloat()
        } catch (e : Exception){
            error = true
        }

    }

    /**
     *
     * getResultat()
     *
     * Retourne le résultat de l'opération stockée dans res
     *
     */
    fun getResultat(): Float {
        return res
    }

    /**
     *
     * isError()
     *
     * Retourne vrai si l'opération a lever une exception
     *
     */
    fun isError() : Boolean {
        return error
    }
}
