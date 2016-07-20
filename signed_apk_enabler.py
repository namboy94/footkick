android_gradle_file = "footkick-android/build.gradle"

with open(android_gradle_file, 'r') as gradle:
    content = gradle.read()

content = content.replace("//SIGNED-KEY-RELEASE", "")

with open(android_gradle_file, 'w') as gradle:
    gradle.write(content)