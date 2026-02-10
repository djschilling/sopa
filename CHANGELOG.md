# Changelog

## Unreleased

* migrate build toolchain to modern Android setup (AGP 8.3.2, Gradle 8.5)
* update project for current Android targets (compile/target SDK 34)
* vendor `AndEngine` into repository (remove git submodule linkage)
* fix legacy `AndEngine` compatibility issues for modern Android APIs
* add project contributor guidance in `AGENTS.md`
* refresh README build/setup documentation

# 2.2.0

* add level count to game over screen in just play modus #66

# 2.2.0

* adjust difficulty in just play modus
* ui improvements in just play modus

# 2.1.0

* bugfixes in gameScene #62, #61
* show confetti when is new highscore #64
* add reload button in just play score scene #63

# 2.0.0

* introduce new modus: Just play modus #46
* add end scene for level mode #52
* fix bug in gamescene when swiping fast


# 1.2.1

* remove restart button on solved game.

# 1.2.0

* add swipe to level-choice-scene #1
* add highlighting for level solving.
* improve image quality.

## 1.1.1

### Bugfixes

* fix performance issues.

## 1.1.0

* first App Store version.

## 1.0.2

### Bugfixes

* prevent app from crashing, when using the back button in last screen of tutorial. #49
* fix spelling error on score screen. #51
* fix wrong minimum moves. #28 #56

### Improvements

* add a link to the twitter app on menu scene. #54
* add possibility to share thoughts about app from menu scene. #55
* add SOPA writing and additional animations to menu scene.


## 1.0.1

### Bugfixes

* prevent app from crash #37
* fix minimum moves in level 100 #40
* prevent music from playing when resuming to scorescreen or gamescene #36
* fix inconsistenct between mute state and mute button #34

### Improvements

* decrease time to wait between tutorial and first level #41
