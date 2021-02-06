Navi-Server Launcher
=====================

Intro
-----
현재 Release되어 있는 서버 Jar파일을 다운 받아 커스텀 프로퍼티 파일을 자동을 생성하고, 실행까지 함께하는[?] 런쳐입니다.

다운로드
------
[ServerCustomLauncher-1.0-SNAPSHOT.jar](https://github.com/Navi-Cloud/Server-Launcher/releases/download/V1.0.0/ServerCustomLauncher-1.0-SNAPSHOT.jar)

사용 방법[macOS/Linux/Windows 동일]
-----------------
자바가 깔려 있다는 가정 하에,
```
$ java -jar launcher.jar "root_path"
```
이고, 
`root_path`는
- 서버 루트를 의미하며
- 반드시 절대경로여야 하고
- 실질적으로 존재하는 폴더여야 합니다.

예시 - Windows + Powershell
-------------
```
$ java -jar .\ServerCustomLauncher-1.0-SNAPSHOT.jar "C:\Users\KangDroid\Desktop\kdrRoot"
```

주의
---
서버가 완전히 실행될 때 까지 걸리는 시간은 최소 4초 ~ 30초 이내입니다. 루트 디렉토리 구조가 복잡할 수록, 루트 내에 파일 수가 많을 수록 부팅 속도는 느려집니다.

서버가 부팅 되고 나서, 반드시 `Started MainServerKt in x seconds (JVM running for x)` 라는 문구가 떠야지 서버가 정상적으로 실행된 것이며,
이러한 문구가 나오지 않고 이상한 Exception이 뜬다면 Issue에 제보해주세요.

기본 구조[자동 생성 디렉토리]
----------------------
해당 프로그램은 JVM에서 정의하는 임시 디렉토리에 testServer.jar 파일과, application-test.properties 파일을 자동적으로 생성합니다.<br><br>
testServer.jar은 첫 한번만 다운 받고, 다음 실행 부터는 임시 디렉토리에서 파일이 삭제되지 않는 한, 다운을 스킵하고 저장된 서버 파일만 쓰고, 
application-test.properties는 루트 폴더가 실행 때 마다 바뀔 수 있어 매번 삭제-재생성 합니다. 저 같은 경우는,
```
서버 다운로드 위치: C:\Users\KangDroid\AppData\Local\Temp\testServer.jar
서버 설정 위치: C:\Users\KangDroid\AppData\Local\Temp\application-test.properties
```
에 위치합니다.
