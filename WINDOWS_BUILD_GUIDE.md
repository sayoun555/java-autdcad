# Windows 완전 독립 실행 파일 빌드 가이드

## 🎯 목표

**Java도, Python도 설치 안 된 Windows PC에서 더블클릭만으로 CLI가 실행되는 .exe 파일 만들기**

---

## 📋 Windows PC에서 실행할 단계

### 1단계: Java 21 JDK 설치 (빌드용, 한 번만)

**주의**: .exe 만들 때만 필요합니다. 만든 .exe는 Java 없이도 실행됩니다!

1. https://adoptium.net/ 접속
2. **Temurin 21 (LTS) JDK** 다운로드
3. 설치 시 **"Add to PATH"** 체크 ✅

**확인:**
```cmd
java -version
```
→ "openjdk version 21" 나오면 성공!

---

### 2단계: 프로젝트 폴더를 Windows PC로 복사

전체 `java-autocad` 폴더를 USB 또는 클라우드로 Windows PC에 복사하세요.

---

### 3단계: Python Embedded 설정

**`setup-python.bat` 파일을 더블클릭**하면:
- Python 3.11 Embedded 자동 다운로드 (약 20MB)
- ezdxf 라이브러리 자동 설치
- `python-embedded` 폴더 생성

**이 단계는 한 번만 실행하면 됩니다!**

---

### 4단계: 완전 독립 실행 파일 생성

**`create-exe-full.bat` 파일을 더블클릭**하면:

1. JAR 파일 자동 빌드
2. Python + ezdxf 패키징
3. Java Runtime 포함
4. **AutoCAD-1.0.exe 완성!** (약 100-150MB)

**소요 시간**: 약 5-10분

---

### 5단계: .exe 배포

생성된 `AutoCAD-1.0.exe`를 아버지 PC로 복사하고:

1. **더블클릭으로 설치**
2. 설치 완료 후 프로그램 실행
3. **CLI가 자동으로 켜지고 프로그램 실행!**

---

## 🎁 최종 결과물

### 생성되는 파일
- **파일명**: `AutoCAD-1.0.exe`
- **크기**: 약 100-150MB
- **내용**: Java Runtime + Python Embedded + ezdxf + 모든 코드

### 설치 후 구조
```
C:\Program Files\AutoCAD\
  ├── app\
  │   └── java-autocad-0.0.1-SNAPSHOT.jar
  ├── runtime\
  │   ├── bin\ (Java Runtime)
  │   └── python\ (Python Embedded + ezdxf)
  └── python\
      └── dxf_to_json.py
```

### Python 자동 경로 설정
프로그램이 실행될 때 자동으로 내장 Python을 찾습니다:
1. `C:\Program Files\AutoCAD\runtime\python\python.exe`
2. `C:\Program Files\AutoCAD\python\dxf_to_json.py`

**코드 수정 없이 자동으로 작동합니다!**

---

## ⚠️ 문제 해결

### Python 경로 오류 발생 시

만약 Python을 찾지 못한다는 오류가 나면, 프로그램 설치 폴더에 `.env` 파일을 만들고:

```
PYTHON_PATH=C:\Program Files\AutoCAD\runtime\python\python.exe
PYTHON_SCRIPT=C:\Program Files\AutoCAD\python\dxf_to_json.py
```

### "java를 찾을 수 없습니다" (빌드 시)
- Java 21 **JDK** 설치 확인
- JRE가 아닌 **JDK**를 설치해야 함

### setup-python.bat 실행 오류
- 인터넷 연결 확인
- 방화벽이 PowerShell을 차단하지 않는지 확인

---

## 📦 빠른 체크리스트

### 빌드 PC (Windows, 한 번만)
- [ ] Java 21 JDK 설치
- [ ] 프로젝트 폴더 복사
- [ ] `setup-python.bat` 실행
- [ ] `create-exe-full.bat` 실행
- [ ] `AutoCAD-1.0.exe` 생성 확인

### 배포 PC (아버지 PC, 사용자)
- [ ] `AutoCAD-1.0.exe` 복사
- [ ] 더블클릭으로 설치
- [ ] 프로그램 실행
- [ ] ✅ 완료!

---

## 🎯 핵심 포인트

### ✅ 장점
- **코드 수정 없음**: Java 코드 전혀 수정하지 않음
- **완전 독립**: Java/Python 설치 불필요
- **간단 배포**: .exe 파일 하나만 복사
- **자동 실행**: 더블클릭으로 CLI 자동 실행
- **경로 자동**: Python 경로 자동으로 찾음

### ⚠️ 주의사항
- **빌드는 Windows에서**: Mac에서는 .exe 만들 수 없음
- **JDK 필수**: 빌드할 때만 필요 (실행 시 불필요)
- **한 번만**: Python setup은 한 번만 실행
- **시간 소요**: 빌드에 5-10분 소요

---

## 📖 파일 설명

| 파일 | 용도 | 설명 |
|-----|------|------|
| `setup-python.bat` | Python 설치 | Python Embedded + ezdxf 자동 설치 |
| `create-exe-full.bat` | .exe 빌드 | Java + Python 포함 .exe 생성 |
| `AutoCAD-1.0.exe` | 최종 결과물 | 배포용 독립 실행 파일 |

---

## 🚀 시작하기

```cmd
# 1. setup-python.bat 더블클릭 (처음 한 번만)
# 2. create-exe-full.bat 더블클릭 (빌드)
# 3. AutoCAD-1.0.exe 생성 확인!
```

**이제 이 .exe 파일을 아무 Windows PC로 복사해서 더블클릭만 하면 됩니다!**
