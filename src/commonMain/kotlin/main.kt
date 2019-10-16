import com.soywiz.klock.seconds
import com.soywiz.korge.*
import com.soywiz.korge.animate.AnLibrary
import com.soywiz.korge.animate.serialization.readAni
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.color.Colors
import com.soywiz.korim.format.*
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.*
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.interpolation.Easing

suspend fun main() = Korge(width = 1080, height = 1920, bgcolor = Colors["#2b2b2b"]) {





	val minDegrees = (-16).degrees
	val maxDegrees = (+16).degrees



	var mainLibrary: AnLibrary = resourcesVfs["chara_kurage.ani"].readAni(views)
	var wall = resourcesVfs["banner_brown.ani"].readAni(views)

	launchImmediately {

		val kurage = mainLibrary.createMainTimeLine().apply {
			position(540, 960)
			scale(3.0, 3.0)
		}
		(0..10).forEach {
			containerRoot.addChild(wall.createMainTimeLine().apply {
				println(width)
				x = 144.0 * it
			})
		}

		containerRoot.addChild(kurage)




		while(true) {

			//kurage.playAndWaitStop("walk_left")

			kurage.tween(kurage::rotation[minDegrees], time=1.seconds, easing = Easing.EASE_IN_OUT)
			kurage.tween(kurage::rotation[maxDegrees], time=1.seconds, easing = Easing.EASE_IN_OUT)
		}

//		mainLibrary.createMainTimeLine()
//		while (true) {
//			image.tween(image::rotation[minDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
//			image.tween(image::rotation[maxDegrees], time = 1.seconds, easing = Easing.EASE_IN_OUT)
//		}

	}
}