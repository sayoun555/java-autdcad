# java-autocad

## 기능 목록
- [x] 도면 변경 감시
- [x] 도면 분석
- [x] ai 연동
- [x] ai 질의 구성
- [x] 결과 출력
- [x] 좌표 처리 확인
- [x] 객체 조립 공장
- [x] 계산 일급 컬렉션
- [x] 전처리를 위한 연산 로직
- [x] json 파싱 기능
- [x] 연산 일급 컬렉션
- [x] Ai 맥락 프롬프트 기능
- [x] .env 처리 기능 구현
- [x] 레이어 속 엔티티 필터링 기능 구현

## 예외처리
- [x] 입력마다 경로, 폴더 찾을 수 없음, 디렉토리 형태인지 확인
- [x] .py파일 형태 여부 와 파이썬 실행 가능한지 확인
- [x] api key 최소 길이 이상인지 검증
- [x] api key 비어있는지 확인
- [x] .env 수정 여부에 y&n가 아니면 예외로 처리

## 리펙터링 목록
- [x] 책임 분산
- [x] 고정 된 파일 입력 경로를 유저가 원하는 경로로 하기 위해 수정
- [x] 파싱 코드 메서드 분리

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