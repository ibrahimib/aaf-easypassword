package io.github.hanjoongcho.utils

import com.nulabinc.zxcvbn.Zxcvbn
import me.gosimple.nbvcxz.Nbvcxz
import me.gosimple.nbvcxz.resources.Configuration
import me.gosimple.nbvcxz.resources.ConfigurationBuilder
import me.gosimple.nbvcxz.resources.Dictionary
import me.gosimple.nbvcxz.scoring.TimeEstimate

/**
 * Created by Administrator on 2017-11-22.
 */

object PasswordStrengthUtils {

    const val OFFLINE_SIMPLE_LOCK = "OFFLINE_SIMPLE_LOCK"
    const val ONLINE_THROTTLED = "ONLINE_THROTTLED"
    const val ONLINE_UNTHROTTLED = "ONLINE_UNTHROTTLED"
    const val OFFLINE_BCRYPT_14 = "OFFLINE_BCRYPT_14"
    const val OFFLINE_BCRYPT_12 = "OFFLINE_BCRYPT_12"
    const val OFFLINE_BCRYPT_10 = "OFFLINE_BCRYPT_10"
    const val OFFLINE_BCRYPT_5 = "OFFLINE_BCRYPT_5"
    const val OFFLINE_SHA512 = "OFFLINE_SHA512"
    const val OFFLINE_SHA1 = "OFFLINE_SHA1"
    const val OFFLINE_MD5 = "OFFLINE_MD5"

    private val mConfiguration: Configuration by lazy {
        val dictionaryList = mutableListOf<Dictionary>()
        ConfigurationBuilder().
                setGuessTypes(mapOf(
                        OFFLINE_SIMPLE_LOCK to 1L,
                        ONLINE_THROTTLED to 2L,
                        ONLINE_UNTHROTTLED to 100L,
                        OFFLINE_BCRYPT_14 to 125L,
                        OFFLINE_BCRYPT_12 to 500L,
                        OFFLINE_BCRYPT_10 to 2000L,
                        OFFLINE_BCRYPT_5 to 64000L,
                        OFFLINE_SHA512 to 5000000000L,
                        OFFLINE_SHA1 to 37336000000L,
                        OFFLINE_MD5 to 115840000000L
                ))
                .setDictionaries(dictionaryList)
                .createConfiguration()
    }

    private val nbvcxz: Nbvcxz by lazy {
        Nbvcxz(mConfiguration)
    }

    private val zxcvbn: Zxcvbn by lazy {
        Zxcvbn()
    }

    fun getTimeToCrack(requestPassword: String, guessType: String): Long {
        val result = nbvcxz.estimate(requestPassword)
        val bigDecimal = TimeEstimate.getTimeToCrack(result, guessType)
        return bigDecimal.toLong()
    }

    fun getTimeToCrackFormatted(requestPassword: String, guessType: String): String {
        val result = nbvcxz.estimate(requestPassword)
        return TimeEstimate.getTimeToCrackFormatted(result, guessType)
    }

//    fun getStrengthLevel(requestPassword: String, guessType: String): Int {
//        return when (getTimeToCrack(requestPassword, guessType)) {
//            in 0..86400 -> 1
//            in 86401..2592000 -> 2
//            in 2592001..31104000 -> 3
//            in 31104001..155520000 -> 4
//            else -> 5
//        }
//    }

//    # 0 Weak        （guesses < ^ 3 10）
//    # 1 Fair        （guesses <^ 6 10）
//    # 2 Good        （guesses <^ 8 10）
//    # 3 Strong      （guesses < 10 ^ 10）
//    # 4 Very strong （guesses >= 10 ^ 10）
    fun getScore(requestPassword: String): Int = zxcvbn.measure(requestPassword).score + 1

}

