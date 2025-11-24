### AutoCAD AI Design Assistant

## 개요
 오토바이 타이어 단면(프로파일) DXF 파일을 실시간으로 분석하고, 구조역학 기반 AI 피드백을 제공하는 CLI 애플리케이션입니다.

## AI 구조역학 해석
OpenAI API를 통한 공학적 분석:
LLM이 계산하는 지표들:

- CGI (Curvature Gradient Index): 곡률 기울기, 횡강성 집중도
- SHS (Structural Heterogeneity Score): 구조 불균일성
- CLTR (Curve-Line Load Transfer Ratio): 곡선/직선 하중 전달 비율
- GDS (G1 Discontinuity Severity): G1 불연속 심각도
- BreakDensity: 곡선 구간당 불연속점 밀도

## 기술 스택
- Java 21: 메인 애플리케이션
- Python 3: DXF 파싱 (ezdxf 라이브러리)
- OpenAI API: GPT-4o-mini
- Gradle: 빌드 도구
- Jackson: JSON 파싱
- ezdxf: Python DXF 라이브러리
- 
## 아키텍처
Controller (AutoCadController)
↓
Service (AutoMeasureService)
↓
├─ DxfConverter → Python Script → JSON
└─ AutoAi → Domain Analysis → OpenAI API
↓
Domain (Line, Arc, Circle, Ellipse)
↓
Statistics (G1, Curvature, Entity)
↓
AI Prompt Builder

## 배포
- Windows 환경을 위한 독립 실행형 패키지로 배포했습니다. 사용자가 Java나 Python을 별도로 설치할 필요가 없도록 모든 런타임을 임베드했습니다.

## 실행 방법
- 더블클릭 또는 CMD에서 AutoCAD-launcher.bat

## 설정
- Python 경로 입력 (예: python/python.exe)
- 스크립트 경로 입력 (예: python/dxf_to_json.py)
- OpenAI API 키 입력
- 감시할 폴더 경로 입력
- 타이어 종류 입력 (선택사항)
- 설정은 .env 파일에 저장되며, 다음 실행 시 재사용됩니다.

## 사용예시
```
감시할 폴더 경로를 입력하세요:
> C:\AutoCAD\Projects\Tire

타이어 종류를 적어주세요
> 스포츠 타이어

완료 → C:\AutoCAD\Projects\Tire\profile.json
곡선: 145, 선: 2847, 이: 2992

### 정량 기반 곡률·강성 분석
Arc 평균 곡률: 0.0234
Circle 평균 곡률: 0.0189
Ellipse 평균 곡률: 0.0301
CGI: 1.59 → 횡강성 집중도가 높음
...

프로그램을 종료하려면 stop을 적어주세요
> stop
종료 하겠습니다
```
## 배포 구조
```
AutoCAD-AI-Assistant/
├── runtime/              # JRE 21 (임베디드)
│   └── bin/
│       └── java.exe
├── python/               # Python 3.x + ezdxf (임베디드)
│   ├── python.exe
│   ├── dxf_to_json.py
│   └── Lib/             # Python 라이브러리
│       └── site-packages/
│           └── ezdxf/
├── app/
│   └── java-autocad.jar # Fat JAR
├── AutoCAD-launcher.bat # 실행 스크립트
└── .env                 # 생성될 설정 파일
```
## 프로젝트 구조
```
AutoCad
├── Application.java               # 실행기
├── controller
│   └── AutoCadController          # 전체 흐름 제어
├── manager
│   ├── FileWatcher                # 파일 감시 & 이벤트 처리
│   └── DxfConverter               # Python 호출
├── ai
│   ├── AutoAi                     # 분석 전체 orchestrator
│   ├── AiClient                   # AI 호출
│   └── AiPrompt                   # 프롬프트 구성
├── parser
│   └── AutoParser                 # JSON → 도메인 파싱
├── domain
│   ├── Point / Line / Arc ...     # 도메인 모델
│   ├── Lines / Arcs ...           # 일급 컬렉션
│   ├── G1 / G1s                   # G1 연속성 도메인
│   └── CurvatureStatistics        # 곡률 변화량 / 곡률변화율 도메인
├── dto
│   ├── 통계 DTO들                 # LineStatisticsDto 등
│   └── AiDto                       # OpenAI 요청 구조
├── service
│   ├── AutoMeasureService          # 인터페이스
│   └── AutoMeasureServiceImpl      # 분석 비즈니스 로직
├── view
│   ├── InputView                   # 사용자 입력
│   ├── OutputView                  # 출력
│   └── InputValidator              # 입력 검증
├── config
│   ├── EnvConfig                   # .env 관리
│   └── ControllerConfig            # DI 조립
└── dxf_to_json.py                  # DXF -> JSON 변환
```