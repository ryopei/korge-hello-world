import com.soywiz.klock.seconds
import com.soywiz.korau.sound.nativeSoundProvider
import com.soywiz.korau.sound.readNativeSound
import com.soywiz.korge.*
import com.soywiz.korge.animate.AnLibrary
import com.soywiz.korge.animate.AnMovieClip
import com.soywiz.korge.animate.serialization.readAni
import com.soywiz.korge.input.*
import com.soywiz.korge.scene.Module
import com.soywiz.korge.scene.Scene
import com.soywiz.korge.scene.sceneContainer
import com.soywiz.korge.tween.*
import com.soywiz.korge.view.*
import com.soywiz.korim.bitmap.NativeImage
import com.soywiz.korim.color.ColorTransform
import com.soywiz.korim.color.Colors
import com.soywiz.korim.color.RGBA
import com.soywiz.korim.font.BitmapFontGenerator
import com.soywiz.korim.font.ttf.TtfFont
import com.soywiz.korim.font.ttf.fillText
import com.soywiz.korim.vector.Context2d
import com.soywiz.korinject.AsyncFactory
import com.soywiz.korinject.AsyncInjector
import com.soywiz.korio.async.async
import com.soywiz.korio.async.launchImmediately
import com.soywiz.korio.file.std.*
import com.soywiz.korio.stream.openSync
import com.soywiz.korma.geom.degrees
import com.soywiz.korma.geom.vector.rect
import com.soywiz.korma.geom.vector.roundRect
import com.soywiz.korma.interpolation.Easing
import kotlinx.coroutines.awaitAll
import kotlin.reflect.KClass


object MyModule : Module() {

    override val mainScene: KClass<out Scene> = TitleScene::class

    override suspend fun AsyncInjector.configure() {
        mapPrototype { TitleScene() }
        mapPrototype { TitleScene2() }
    }
}

suspend fun main() = Korge(config = Korge.Config(module = MyModule))
class TitleScene : Scene() {

    override suspend fun Container.sceneInit() {

        val t = text("Mouse Position:", color = Colors.WHITE)

        graphics {

            stroke(Colors.RED, Context2d.StrokeInfo(3.0)) {
                roundRect(100.0, 100.0, 300.0, 300.0, 10, 10)
            }

            fill(Colors.YELLOW) {
                roundRect(300.0, 300.0, 300.0, 300.0, 10, 10)
            }


            onClick {
                launchImmediately {
                    sceneContainer.changeTo(TitleScene2::class, time = 1.5.seconds)
                }
            }
        }
        onMove {
            t.text = "Mouse Position:" + it.currentPos
        }
    }
    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()
    }

}

class TitleScene2 : Scene() {

    lateinit var kurage:AnMovieClip

    override suspend fun Container.sceneInit() {
        var mainLibrary: AnLibrary = resourcesVfs["chara_kurage.ani"].readAni(views)
        kurage = mainLibrary.createMainTimeLine().apply {
            position(3 * this.width/2, this.height*3)
            scale(3.0, 3.0)
            onClick {
                launchImmediately {
                    sceneContainer.changeTo(TitleScene::class, time = 1.5.seconds)
                }
            }
        }
        kurage.symbol.states.forEach {
            println(it.value.name)
        }

        addChild(kurage)
        kurage.timelineRunner.gotoAndStop("stop_back")
    }

    override suspend fun sceneAfterInit() {
        super.sceneAfterInit()

        async {
            kurage.timelineRunner.gotoAndPlay("walk_left")
            kurage.timelineRunner.gotoAndPlay("walk_left")
            kurage.timelineRunner.gotoAndPlay("walk_left")
            kurage.timelineRunner.gotoAndPlay("walk_left")
            kurage.timelineRunner.gotoAndPlay("walk_left")
            kurage.timelineRunner.gotoAndPlay("walk_left")
        }.await()
    }
}