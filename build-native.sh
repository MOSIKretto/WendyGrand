#!/usr/bin/fish

set -x JAVA_HOME /usr/lib/jvm/java-24-graalvm
set -x PATH $JAVA_HOME/bin $PATH

echo "📦 Сборка JAR..."
./gradlew :app:clean :app:fatJar

set jar_file (find app/build/libs -name "*.jar" -type f | head -1)

echo "🚀 Статическая сборка..."
native-image \
    -jar "$jar_file" \
    wendygrand \
    --no-fallback \
    -H:+JNI \
    -H:+StaticExecutableWithDynamicLibC \
    --enable-url-protocols=http,https,file \
    -H:IncludeResources=".*" \
    --initialize-at-build-time=org.vosk \
    --initialize-at-run-time=com.sun.jna \
    -H:ConfigurationFileDirectories=./native-config \
    -H:+ReportExceptionStackTraces

if test -f "wendygrand"
    echo "🎉 Статический бинарник создан"
    ls -lh wendygrand
    echo "🔍 Проверка:"
    file wendygrand
    echo "Бинарник должен быть статически слинкован"
else
    echo "❌ Сборка не удалась"
end