import os
import configparser

keydirectory = os.path.join(os.path.expanduser("~"), ".android-keys")
keyfile = os.path.join(keydirectory, "key")
config_file = os.path.join(keydirectory, "config")

config = configparser.ConfigParser()
config.read(config_file)
parsed_config = dict(config.items("credentials"))

store_password = parsed_config["store_password"]
key_password = parsed_config["key_password"]
key_alias = parsed_config["key_alias"]

gradle_build_file = os.path.join(os.getcwd(), "footkick-android/build.gradle")

with open(gradle_build_file, 'r') as f:
    gradle_build = f.read()

gradle_build = gradle_build.replace("//@@@SIGNINGCONFIG@@@", "")
gradle_build = gradle_build.replace("/*@@@STARTCREDENTIALS", "")
gradle_build = gradle_build.replace("@@@ENDCREDENTIALS*/", "")

gradle_build = gradle_build.replace("@@@KEYFILE@@@", keyfile)
gradle_build = gradle_build.replace("@@@STOREPASS@@@", store_password)
gradle_build = gradle_build.replace("@@@KEYALIAS@@@", key_alias)
gradle_build = gradle_build.replace("@@@KEYPASS@@@", key_password)

with open(gradle_build_file, 'w') as f:
    f.write(gradle_build)
