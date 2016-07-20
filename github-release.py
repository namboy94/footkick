#!/usr/bin/python

import os
import sys
from subprocess import check_output

# Change project specific stuff here
root_gradle_file = "build.gradle"
repoowner = "namboy94"
reponame = "footkick"

release_assets = [{"filename_pre_version": "footkick-java-",
                   "filename_post_version": ".jar",
                   "filepath": "footkick-java/build/libs/",
                   "content_type": "application/java-archive"},
                  {"filename_pre_version": "footkick-lib-",
                   "filename_post_version": ".jar",
                   "filepath": "footkick-lib/build/libs/",
                   "content_type": "application/java-archive"},
                  {"filename_pre_version": "footkick-android-release-",
                   "filename_post_version": ".apk",
                   "filepath": "footkick-android/build/outputs/apk/",
                   "content_type": "application/vnd.android.package-archive"},
                  {"filename_pre_version": "footkick-android-release-noanalytics-",
                   "filename_post_version": ".apk",
                   "filepath": "footkick-android/build/outputs/apk/",
                   "content_type": "application/vnd.android.package-archive"}]

# Don't edit past here!


oauth_token = sys.argv[1]
version = ""

repopath = "repos/" + repoowner + "/" + reponame + "/releases"
api_repo_url = "https://api.github.com/" + repopath
upload_repo_url = "https://uploads.github.com/" + repopath
oauth_param = "access_token=" + oauth_token

with open(root_gradle_file, 'r') as gradlefile:
    for line in gradlefile.read().split("\n"):
        if line.startswith("    version = \""):
            version = line.split("version = \"")[1].split("\"")[0]

create_release = ["curl",
                  "-X",
                  "POST",
                  api_repo_url + "?" + oauth_param,
                  "-d",
                  "{\"tag_name\": \"" + version + "\"," +
                  " \"target_commitish\": \"master\"," +
                  "\"name\":\"" + version + "\"," +
                  "\"body\": \"Automatic Release Build\"," +
                  "\"draft\": false," +
                  "\"prerelease\": false}"]

response = check_output(create_release).decode()
tag_id = response.split("\"id\": ")[1].split(",")[0]

jarfilename = "footkick-java-" + version + ".jar"
jarfile = "footkick-java/build/libs/" + jarfilename

uploaded_binary_command = "curl -X POST --header \"Content-Type:application/java-archive\" --data-binary @" + jarfile


uploaded_binary_command += " 'https://uploads.github.com/repos/namboy94/footkick/releases/" + tag_id + "/assets?name=" + jarfilename
uploaded_binary_command += "&access_token=" + oauth_token + "'"


print(uploaded_binary_command)



for asset in release_assets:
    filename = asset["filename_pre_version"] + version + asset["filename_post_version"]
    filepath = asset["filepath"] + filename
    content_type = asset["content_type"]

    upload_binary = ["curl",
                     "-X",
                     "POST",
                     "--header",
                     "\"Content-Type:" + content_type + "\"",
                     "--data-binary",
                     "@" + filepath,
                     "'" + upload_repo_url + "/" + tag_id + "/assets?name=" + filename + "&" + oauth_param + "'"]

    params = ""
    for param in upload_binary:
        params += param + " "
    params = params[0:-1]

    os.system(params)
