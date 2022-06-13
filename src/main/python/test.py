import tensorflow as tf
from tensorflow import keras
from tensorflow.keras import layers
import numpy as np
model = keras.Sequential([keras.layers.Dense(units = 1, input_shape=[1])])
model.compile(optimizer = 'sgd', loss = 'mean_squared_error')

xs = np.array([1,2,3,4,5,6,7,8,9,10,11,12])
ys = np.array([3,4,5,6,7,8,9,10,11,12,13,14])

model.fit(xs,ys,epochs = 1000)

print(model.predict([13]))