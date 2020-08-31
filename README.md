# SePGP

This repo contains the source code I wrote as part of my master thesis. It was never intended to be released publicly therefore it's not commented a lot. However since the implementation worked really well and it might be useful to others I'm releasing it here. Feel free to ask any questions or to create a PR if you find any bugs or have some improvements.

The source code is divided into two parts: The first part is my own implementation of the push programming language, more specifically the Plush dialect. It can be found in the ``push`` package. The second part is a genetic programming algorithm that uses the Plush implementation and tracks a lot of meta information during GP runs. It's located in the ``gp`` package.
Although they are intended to be used together the Plush implementation can be used independently of the GP algorithm.

An example on how to use both in conjunction is shown in the ``Main`` class. If you'd like to use the Plush implementation as standalone there is an example in the ``PlushStandalone`` class.

More details can be found in the thesis itself which can be downloaded from (TODO: Add link)(Only in German).