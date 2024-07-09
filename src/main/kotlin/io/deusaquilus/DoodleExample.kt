package io.deusaquilus

import io.exoquery.terpal.Interpolator
import io.nacular.doodle.application.Application
import io.nacular.doodle.application.application
import io.nacular.doodle.core.*
import io.nacular.doodle.drawing.*
import io.nacular.doodle.drawing.Color.Companion.Black
import io.nacular.doodle.drawing.Color.Companion.Red
import io.nacular.doodle.drawing.Color.Companion.White
import io.nacular.doodle.drawing.Color.Companion.Yellow
import io.nacular.doodle.geometry.Circle
import io.nacular.doodle.geometry.Point
import io.nacular.doodle.geometry.Point.Companion.Origin
import io.nacular.doodle.text.*
import io.nacular.doodle.text.Target.Background
import org.kodein.di.instance
import kotlin.math.min


object Styled: Interpolator<StyledText, StyledText> {
  override fun interpolate(parts: () -> List<String>, params: () -> List<StyledText>): StyledText {
    var output = StyledText("")
    val parts = parts().iterator()
    val params = params().iterator()
    while (parts.hasNext()) {
      output = output..parts.next()
      if (params.hasNext()) {
        output = output..params.next()
      }
    }
    return output
  }
}


class MyApp(display: Display): Application {
  init {
    display += view {
      val bold = TextDecoration(
        lines     = setOf(),
        color     = Black,
        thickness = TextDecoration.Thickness.Absolute(2.0),
        style     = TextDecoration.Style.Solid
      )

      val decoration = TextDecoration(
        lines     = setOf(TextDecoration.Line.Under),
        color     = Red,
        thickness = TextDecoration.Thickness.Absolute(1.0),
        style     = TextDecoration.Style.Wavy
      )

//      val text = bold("Lorem Ipsum") .." is simply "..Yellow("dummy text", target = Target.Background)..
//        " of the printing and typesetting industry. It has been the industry's standard dummy text "..
//        decoration("ever since the 1500s")..
//        ", when an unknown printer took a galley of type and scrambled it to make a type specimen book."

      val text =
        Styled("${bold("Lorem Ipsum")} is simply ${Yellow("dummy text", target = Background)} of the printing and typesetting industry. It has been the industry's standard dummy text ${decoration("ever since the 1500s")}, when an unknown printer took a galley of type and scrambled it to make a type specimen book.")

      size   = display.size
      render = {
        rect(bounds.atOrigin, fill = White.paint)

        wrapped(
          text        = text,
          at          = Origin,
          width       = this.width,
          textSpacing =  TextSpacing(wordSpacing = 5.0, letterSpacing = 1.0),
          lineSpacing = 1.2f
        )
      }
    }
  }
  // ...

  override fun shutdown() { /*...*/ }
}

fun main() {
  application {
    MyApp(display = instance())
  }
}