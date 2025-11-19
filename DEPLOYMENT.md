# AutoCAD CLI - Windows 완전 독립 실행 파일 만들기

## 🎯 목표: Java/Python 없이 실행 가능한 .exe 파일

**당신이 원하는 것:**
- ✅ Java/Python 없이 실행 가능
- ✅ 모든 패키지가 포함된 독립 실행 파일
- ✅ 더블클릭만으로 CLI 실행

**이 가이드로 만들 수 있는 것:**
- **AutoCAD-1.0.exe** (Java + Python 포함, 약 100-150MB)
- 아무 Windows PC에서 더블클릭으로 바로 실행 가능
- Java, Python 설치 필요 없음!

---

## 🚀 Windows PC에서 완전 독립 실행 파일 만들기

### 1단계: 프로젝트를 Windows PC로 복사

전체 프로젝트 폴더를 USB나 클라우드로 Windows PC에 복사하세요:

```
📁 java-autocad/ (전체 폴더)
```

### 2단계: Windows PC에 Java 21 JDK 설치 (한 번만)

**주의:** .exe 파일을 **만들 때만** Java가 필요합니다.
만든 .exe 파일은 Java 없이도 실행됩니다!

1. https://adoptium.net/ 접속
2. "Temurin 21 (LTS) **JDK**" 다운로드
3. 설치 시 "Add to PATH" 체크

**Java JDK 설치 확인:**
```cmd
java -version
```

### 3단계: Python Embedded 설정

Windows PC에서 **setup-python.bat**을 **더블클릭**하면:
- Python 3.11 Embedded 자동 다운로드
- ezdxf 라이브러리 자동 설치
- python-embedded 폴더 생성

```cmd
setup-python.bat
```

이 단계는 **한 번만** 실행하면 됩니다!

### 4단계: 완전 독립 실행 파일 생성

Windows PC에서 **create-exe-full.bat**을 **더블클릭**하면:

1. JAR 파일 자동 빌드
2. Python + ezdxf 패키징
3. Java Runtime 포함
4. **AutoCAD-1.0.exe** 완성!

```cmd
create-exe-full.bat
```

**생성되는 파일:**
- `AutoCAD-1.0.exe` (약 100-150MB)
- Java Runtime + Python Embedded + ezdxf 모두 포함!

### 5단계: .exe 파일 배포 및 실행

생성된 **AutoCAD-1.0.exe**를:
- 아무 Windows PC로 복사
- **더블클릭으로 설치**
- 설치 후 프로그램 실행
- **Java/Python 설치 필요 없음!**

---

## 💡 방법 비교

| 방법 | Java/Python 필요? | 파일 크기 | 포함 항목 | 추천 |
|-----|------------------|---------|----------|------|
| **create-exe-full.bat** | ❌ 불필요 | 100-150MB | Java + Python + ezdxf | **✅ 최고 추천!** |
| AutoCAD.bat (JAR) | ✅ 필요함 | 15MB | 앱만 | 테스트용 |

---

## 📋 빠른 체크리스트

### Windows PC에서 할 일 (처음 한 번만):
1. ✅ Java 21 JDK 설치 (https://adoptium.net/)
2. ✅ 프로젝트 폴더 복사
3. ✅ `setup-python.bat` 더블클릭 (Python 설정)
4. ✅ `create-exe-full.bat` 더블클릭 (.exe 생성)
5. ✅ 생성된 `AutoCAD-1.0.exe`를 아버지 PC로 복사
6. ✅ 아버지 PC에서 더블클릭으로 실행!

### 핵심 포인트:
- **빌드는 한 번만**: Windows PC에서 한 번만 .exe를 만들면 됨
- **Java/Python은 빌드용**: .exe 만들 때만 필요, 실행할 때는 불필요
- **완전 독립**: AutoCAD-1.0.exe 하나에 모든 것이 포함됨
- **배포는 간단**: .exe 파일 하나만 복사하면 끝

---

## 🔧 문제 해결

### "java를 찾을 수 없습니다" 오류 (빌드 시)
- Java 21 **JDK** (개발 키트)를 설치하세요
- 다운로드: https://adoptium.net/
- JRE가 아닌 **JDK**를 설치해야 함

### .exe 파일이 생성되지 않음
- `java -version` 실행 시 "openjdk" 또는 "jdk"가 나와야 함
- "jre"만 나오면 JDK 재설치 필요

### 아버지 PC에서 실행이 안 됨
- AutoCAD-1.0.exe를 더블클릭하여 설치했는지 확인
- 설치 후 시작 메뉴나 바탕화면에서 실행

---

## 📦 Mac에서 빌드한 JAR 파일

현재 Mac에서 JAR 파일이 생성되었습니다:
```
/Users/sanghyunyoun/java-autocad/build/libs/java-autocad-0.0.1-SNAPSHOT.jar
```

**다음 단계:**
1. 전체 프로젝트 폴더를 Windows PC로 복사 (USB/클라우드)
2. Windows에서 `create-exe.bat` 더블클릭
3. 생성된 `AutoCAD-1.0.exe`를 아버지 PC로 복사
4. 아버지 PC에서 더블클릭으로 실행!

---

## 🎁 최종 결과물

**생성되는 파일:**
- `AutoCAD-1.0.exe` (약 100-150MB)
- **포함 항목:**
  - Java Runtime 21
  - Python 3.11 Embedded
  - ezdxf 라이브러리
  - 모든 애플리케이션 코드

**사용 방법:**
1. .exe 파일 더블클릭 → 설치
2. 설치된 프로그램 실행
3. CLI가 열리며 AutoCAD 명령 사용 가능
4. **아무것도 추가 설치할 필요 없음!**

---

## 📖 기술 정보

- **프로그램 이름:** AutoCAD CLI Application
- **메인 클래스:** com.example.javaautocad.AutoCad.Application
- **Java 버전:** 21 (내장)
- **Python 버전:** 3.11 Embedded (내장)
- **Python 라이브러리:** ezdxf (내장)
- **빌드 도구:** Gradle 8.14.3
- **패키징 도구:** jpackage + Embedded Python
- **Spring Boot:** 3.5.7

---

## 📂 생성된 파일 구조

```
AutoCAD-1.0.exe 설치 후:
📁 C:\Program Files\AutoCAD\
  ├── 📁 runtime\
  │   ├── 📁 bin\ (Java Runtime)
  │   └── 📁 python\ (Python Embedded + ezdxf)
  ├── 📁 app\
  │   └── java-autocad-0.0.1-SNAPSHOT.jar
  └── 📁 python\
      └── dxf_to_json.py
```
