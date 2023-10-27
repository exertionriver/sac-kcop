package river.exertion.sac.view

import com.badlogic.gdx.Input
import com.badlogic.gdx.InputProcessor

object SACInputProcessor : InputProcessor {

    override fun keyDown(keycode: Int): Boolean {
        when (keycode) {
            Input.Keys.R -> { println ("R!") }
            Input.Keys.G -> { println ("G!") }
            Input.Keys.B -> { println ("B!") }

            Input.Keys.E -> { println ("E!") }
            Input.Keys.F -> { println ("F!") }
            Input.Keys.V -> { println ("V!") }

            Input.Keys.UP -> { println ("up!") }
            Input.Keys.DOWN -> { println ("down!") }
            Input.Keys.LEFT -> { println ("left!") }
            Input.Keys.RIGHT -> { println ("right!") }
        }
        return false
    }

    override fun keyUp(keycode: Int): Boolean { return false }

    override fun keyTyped(character: Char): Boolean { return false }

    override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchUp(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchCancelled(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean { return false }

    override fun touchDragged(screenX: Int, screenY: Int, pointer: Int): Boolean { return false }

    override fun mouseMoved(screenX: Int, screenY: Int): Boolean { return false }

    override fun scrolled(amountX: Float, amountY: Float): Boolean { return false }
}