package groovyToExeConverter.core.jarToExee.core

import groovyToExeConverter.model.exception.BitmapImageWriterException

import javax.imageio.ImageIO
import java.awt.image.BufferedImage

import static java.awt.RenderingHints.KEY_INTERPOLATION
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC

class BitmapImageWriter {

    /**
     * This takes in an image of type .bmp, .jpg, .jpeg, or .gif and rewrites it to a given bitmap
     * file as a 24-bit bitmap image.  Launch4j only accepts 24-bit bitmaps as splash files, so this
     * method helps workaround that limitation by allowing more image formats and just converting
     * them to 24-bit bitmaps only the fly.
     *
     * @param imageFile the image to be converted to a 24-bit bitmap
     * @param imageFileAsBitmap the destination bitmap image
     * @return the image as a 24-bit bitmap
     */
    static File writeImageAsBitmap(File imageFile, File imageFileAsBitmap) {
        def image = ImageIO.read(imageFile)

        new BufferedImage(image.width, image.height, image.type).with { bufferedImage ->
            createGraphics().with {
                setRenderingHint(KEY_INTERPOLATION, VALUE_INTERPOLATION_BICUBIC)
                drawImage(image, 0, 0, image.width, image.height, null)
                dispose()
            }
            ImageIO.write(bufferedImage, 'bmp', imageFileAsBitmap)

            boolean successfulImageRewrite = ImageIO.write(bufferedImage, 'bmp', imageFileAsBitmap)
            if(!successfulImageRewrite){
                throw new BitmapImageWriterException("Failed to rewrite '$imageFile' as a 24-bit BMP file.")
            }
        }

        return imageFileAsBitmap
    }

}
