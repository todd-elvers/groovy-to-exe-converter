package groovyToExeConverter.fileConversion.converterHelpers

import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC
import static org.apache.commons.io.FilenameUtils.removeExtension

class BitmapImageWriter {

    static File writeImageAsBitmap(File imageFile, File imageFileAsBitmap) {
        def image = ImageIO.read(imageFile)

        new BufferedImage(image.width, image.height, image.type).with { bufferedImage ->
            createGraphics().with {
                setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC)
                drawImage(image, 0, 0, image.width, image.height, null)
                dispose()
            }
            ImageIO.write(bufferedImage, 'bmp', imageFileAsBitmap)
        }

        return imageFileAsBitmap
    }

}
