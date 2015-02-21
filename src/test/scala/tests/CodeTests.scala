package tests

import com.mle.push.adm.ADMClient
import com.mle.push.android.AndroidMessage
import com.mle.push.gcm.GCMClient
import com.mle.push.mpns.{MPNSClient, ToastMessage}
import com.ning.http.client.Response
import org.scalatest.FunSuite

import scala.concurrent.Future
import scala.concurrent.duration.DurationInt

/**
 * @author Michael
 */
class CodeTests extends FunSuite {
  test("Google example") {
    val gcmApiKey: String = ???
    val deviceRegistrationId: String = ???
    val client = new GCMClient(gcmApiKey)
    val message = AndroidMessage(Map("key" -> "value"), expiresAfter = 20.seconds)
    val response: Future[Response] = client.push(deviceRegistrationId, message)
  }
  test("Amazon example") {
    val clientId: String = ???
    val clientSecret: String = ???
    val deviceID: String = ???
    val client = new ADMClient(clientId, clientSecret)
    val message = AndroidMessage(Map("key" -> "value"), expiresAfter = 20.seconds)
    val response: Future[Response] = client.push(deviceID, message)
  }
  test("MPNS example") {
    val deviceURL: String = ???
    val client = new MPNSClient
    val message = ToastMessage("text1", "text2", deepLink = "/App/Xaml/DeepLinkPage.xaml?param=value", silent = true)
    val response: Future[Response] = client.push(deviceURL, message)
  }
}
