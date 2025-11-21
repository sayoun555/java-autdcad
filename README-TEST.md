# 테스트 가이드

## 1. Python 스크립트 테스트

**목적:** Python 스크립트가 DXF 파일을 정상적으로 처리하는지 확인

**실행:**
```bat
test-python.bat
```

**예상 결과:**
- JSON 형식의 출력 (LINE, CIRCLE, ARC 엔티티 정보)
- 에러 없이 완료

---

## 2. JAR 빌드 테스트

**목적:** Gradle 빌드가 정상적으로 작동하는지 확인

**실행:**
```bat
test-jar.bat
```

**예상 결과:**
- `build/libs/java-autocad-0.0.1-SNAPSHOT.jar` 생성됨
- 파일 크기: 약 50-60MB

---

## 3. 전체 패키지 테스트

**목적:** 최종 배포 패키지가 정상적으로 생성되는지 확인

**실행:**
```bat
1. setup-python.bat (처음 한 번만)
2. create-package.bat
```

**예상 결과:**
- `AutoCAD-Portable.zip` 생성됨 (100-150MB)
- 압축 해제 후 폴더 구조:
  ```
  AutoCAD-Portable/
  ├── .env
  ├── java-autocad-0.0.1-SNAPSHOT.jar
  ├── run.bat
  ├── python/
  │   └── dxf_to_json.py
  └── runtime/
      └── python/
  ```

---

## 4. 실행 테스트

**목적:** 패키지가 정상적으로 실행되는지 확인

**실행:**
1. `AutoCAD-Portable.zip` 압축 해제
2. `.env` 파일에 API 키 입력
3. `run.bat` 실행
4. 테스트 폴더 경로 입력 (test-sample.dxf가 있는 폴더)
5. 타이어 모드 선택 (y 또는 n)

**예상 결과:**
- CLI가 정상적으로 시작됨
- 폴더 모니터링 시작
- DXF 파일 변경 감지 및 처리

---

## 문제 해결

### Python 에러
```
Error: Run setup-python.bat first!
```
→ `setup-python.bat` 실행 필요

### JAR 빌드 에러
```
Error: Build failed!
```
→ Java 21 JDK 설치 확인

### ZIP 생성 에러
```
python-embedded not found
```
→ `setup-python.bat` 먼저 실행

### 실행 시 .env 에러
```
env 파일 불러오기 실패
```
→ `.env` 파일에 API 키 입력 확인
