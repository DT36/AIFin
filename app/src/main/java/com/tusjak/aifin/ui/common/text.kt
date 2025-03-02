package ui.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.*
import androidx.compose.ui.tooling.preview.Preview
import com.tusjak.aifin.common.M
import com.tusjak.aifin.theme.caption1
import com.tusjak.aifin.theme.tintAccent
import com.tusjak.aifin.theme.tintPrimary
import com.tusjak.aifin.theme.value

@Composable
fun AfText(
  text        : AnnotatedString,
  modifier    : Modifier = M,
  style       : TextStyle,
  color       : Color = Color.Unspecified,
  textAlign   : TextAlign? = null,
  maxLines    : Int = Int.MAX_VALUE,
  minLines    : Int = 1,
  onTextLayout: (TextLayoutResult) -> Unit = {}
) = Text(
  text         = text,
  modifier     = modifier,
  style        = style,
  color        = color,
  overflow     = TextOverflow.Ellipsis,
  textAlign    = textAlign,
  maxLines     = maxLines,
  minLines     = minLines,
  onTextLayout = onTextLayout
)

@Composable
fun AfText(
  text        : String,
  modifier    : Modifier = M,
  style       : TextStyle,
  color       : Color = Color.Unspecified,
  textAlign   : TextAlign? = null,
  maxLines    : Int = Int.MAX_VALUE,
  minLines    : Int = 1,
  onTextLayout: (TextLayoutResult) -> Unit = {}
) = AfText(AnnotatedString(text), modifier, style, color, textAlign, maxLines, minLines, onTextLayout)

@Composable
fun AfClickableText(
  modifier : Modifier = M,
  text     : AnnotatedString,
  style    : TextStyle = caption1,
  onClick  : (String) -> Unit
) {
  ClickableText(
    modifier = modifier,
    text     = text,
    style    = style,
    onClick  = { offset ->
      text.getStringAnnotations(start = offset, end = offset).firstOrNull()?.let { link -> onClick(link.item) }
    })
}

fun String.formatHTML(
  applyBold: SpanStyle.()   -> SpanStyle = { copy(fontWeight = FontWeight.Bold, color = if(color == Color.Unspecified) tintPrimary.value else color) },
  applyItalic: SpanStyle.() -> SpanStyle = { copy(fontStyle = FontStyle.Italic) },
  applyLink: SpanStyle.()   -> SpanStyle = { copy(color = tintAccent.value, textDecoration = TextDecoration.Underline) },
  defaultStyle: SpanStyle = SpanStyle()
) = buildAnnotatedString {

  var activeUrlId = 0
  var activeText = replace("""<br/?>""".toRegex(), "\n").replace("</br>", "")
  var activeStyle = defaultStyle

  val supportedTags = listOf("<b>", "</b>", "<strong>", "</strong>", "<a", "</a>", "<i>", "</i>")

  pushStyle(SpanStyle())

  while(activeText.isNotEmpty()) {

    // pick tag and append everything before it without change
    val tag = supportedTags.minBy { tag -> activeText.indexOf(tag).let { if (it == -1) Int.MAX_VALUE else it} }
    append(activeText.substringBefore(tag))

    // break in case tag wasn't found
    if (!activeText.contains(tag)) break

    activeText = activeText.substringAfter(tag)

    when(tag) {
      "<strong>" -> pushStyle(activeStyle.applyBold().also { activeStyle = it })
      "<b>" -> pushStyle(activeStyle.applyBold().also { activeStyle = it })
      "<i>" -> pushStyle(activeStyle.applyItalic().also { activeStyle = it })
      "<a" -> {
        pushStyle(activeStyle.applyLink().also { activeStyle = it })
        val url = activeText.substringAfter("href=").substringBefore(">").drop(1).dropLast(1) // drop "" characters (and possibly ” or similar)
        pushStringAnnotation("${activeUrlId++}", url)
        activeText = activeText.substringAfter(">")
      }
      else -> runCatching{
        when(tag) {
          "</a>" -> { pop(); pop() }
          else -> pop()
        }
      }
    }
  }
}

fun AnnotatedString.Builder.appendLink(text: String, tag: String = "URL") =
  withStyle(
    style = SpanStyle(
      color          = tintAccent.value,
      fontWeight     = caption1.fontWeight,
      fontSize       = caption1.fontSize,
      letterSpacing  = caption1.letterSpacing,
      textDecoration = TextDecoration.Underline),
    block = {
      pushStringAnnotation(tag, "")
      append(text)
      pop()
    })

@Preview
@Composable
fun AfClickableTextPreview(){
  AfClickableText(
    text = buildAnnotatedString {
      append("Zaciatok vety ")
      appendLink("klikaj")
      append(" koniec vety")
    },
    onClick = {})
}

@Preview
@Composable
fun AfClickableTextPreviewBold() {
  Column {
    AfClickableText(text = "<b>Bold</b>".formatHTML(), onClick = {})
    AfClickableText(text = "<b>Bold</b> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b>Bold</b>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b>Bold</b> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<b>Bold trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b>Bold".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b>Bold trailing".formatHTML(), onClick = {})
  }
}

@Preview
@Composable
fun AfClickableTextPreviewItalic() {
  Column {
    AfClickableText(text = "<i>Italic</i>".formatHTML(), onClick = {})
    AfClickableText(text = "<i>Italic</i> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i>Italic</i>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i>Italic</i> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<i>Italic trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i>Italic".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i>Italic trailing".formatHTML(), onClick = {})
  }
}

@Preview
@Composable
fun AfClickableTextPreviewStrong() {
  Column {
    AfClickableText(text = "<strong>Strong</strong>".formatHTML(), onClick = {})
    AfClickableText(text = "<strong>Strong</strong> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <strong>Strong</strong>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <strong>Strong</strong> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<strong>Strong trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <strong>Strong".formatHTML(), onClick = {})
    AfClickableText(text = "leading <strong>Strong trailing".formatHTML(), onClick = {})
  }
}

@Preview
@Composable
fun AfClickableTextPreviewHyperlink() {
  Column {
    AfClickableText(text = "<a href=\"google.sk\">Link</a>".formatHTML(), onClick = {})
    AfClickableText(text = "<a href=\"google.sk\">Link</a> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <a href=\"google.sk\">Link</a>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <a href=\"google.sk\">Link</a> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<a href=\"google.sk\">Link trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <a href=\"google.sk\">Link".formatHTML(), onClick = {})
    AfClickableText(text = "leading <a href=\"google.sk\">Link trailing".formatHTML(), onClick = {})
  }
}

@Preview
@Composable
fun AfClickableTextPreviewBoldItalicCombo() {
  Column {
    AfClickableText(text = "<b><i>Bold Italic</i></b>".formatHTML(), onClick = {})
    AfClickableText(text = "<b><i>Bold Italic</i></b> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b><i>Bold Italic</i></b>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b><i>Bold Italic</i></b> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<b><i>Bold Italic trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b><i>Bold Italic".formatHTML(), onClick = {})
    AfClickableText(text = "leading <b><i>Bold Italic trailing".formatHTML(), onClick = {})

    AfClickableText(text = "<i><b>Bold Italic</b></i>".formatHTML(), onClick = {})
    AfClickableText(text = "<i><b>Bold Italic</b></i> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i><b>Bold Italic</b></i>".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i><b>Bold Italic</b></i> trailing".formatHTML(), onClick = {})
    AfClickableText(text = "<i><b>Bold Italic trailing".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i><b>Bold Italic".formatHTML(), onClick = {})
    AfClickableText(text = "leading <i><b>Bold Italic trailing".formatHTML(), onClick = {})
  }
}
