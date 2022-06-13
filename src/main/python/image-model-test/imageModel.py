import tensorflow_hub as hub
import tensorflow as tf
from matplotlib import pyplot as plt
import numpy as np

import ssl
ssl._create_default_https_context = ssl._create_unverified_context

model = hub.load('https://tfhub.dev/google/magenta/arbitrary-image-stylization-v1-256/2')

def load_image(img_path):
        img = tf.io.read_file(img_path)
        img = tf.image.decode_image(img, channels=3)
        img = tf.image.convert_image_dtype(img, tf.float32)
        img = img[tf.newaxis, :]
        return img

content_image = load_image('van-gogh-2.jpeg')
style_image = load_image('monet.jpeg')

content_image.shape 
#plt.imshow(np.squeeze(style_image))
#plt.show()

outputs = model(tf.constant(content_image), tf.constant(style_image))
stylized_image = outputs[0]

plt.imshow(np.squeeze(stylized_image))
plt.show()
#cv2.imwrite('generated_img.jpg', cv2.cvtColor(np.squeeze(stylized_image)*255,cv2.COLOR_BGR2RGB))

