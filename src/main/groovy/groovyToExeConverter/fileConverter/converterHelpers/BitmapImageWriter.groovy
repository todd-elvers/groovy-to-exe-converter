package groovyToExeConverter.fileConverter.converterHelpers

import org.apache.commons.io.FilenameUtils

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC

class BitmapImageWriter {

    //Supports [bmp, jpg, wbmp, jpeg, png, gif]
    File writeImageAsBitmap(File imageFile){
        def imageAsBitmap = new File(FilenameUtils.removeExtension(imageFile.name) + '.bmp')
        def image = ImageIO.read( imageFile )

        new BufferedImage( image.width, image.height, image.type ).with { bufferedImage ->
            createGraphics().with {
                setRenderingHint( KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC )
                drawImage( image, 0, 0, image.width, image.height, null )
                dispose()
            }
            ImageIO.write( bufferedImage, 'bmp', imageAsBitmap)
        }

        return imageAsBitmap
    }

}
