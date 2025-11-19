import ezdxf
import json
import sys
from collections import defaultdict

TIRE_LAYERS = {'도면층3', 'OUT', 'Layer0$C$L0'}
EXCLUDE_TYPES = {'DIMENSION', 'TEXT', 'MTEXT', 'LEADER', 'HATCH', 'INSERT', 'POINT'}

SAMPLES_CONFIG = {
    'ARC': 1500,
    'ELLIPSE': 200,
    'CIRCLE': 100,
    'LINE': 1000
}

def sample_entities(entities):
    by_type = defaultdict(list)
    for entity in entities:
        by_type[entity['type']].append(entity)

    sampled = []
    stats = {}

    for etype, items in by_type.items():
        count = len(items)
        target = SAMPLES_CONFIG.get(etype, 50)
        sample_size = min(target, count)

        if count <= sample_size:
            sampled.extend(items)
            stats[etype] = f"{count}/{count} (100.0%)"
        else:
            step = count / sample_size
            indices = [int(i * step) for i in range(sample_size)]
            sampled.extend([items[i] for i in indices])
            stats[etype] = f"{sample_size}/{count} ({100*sample_size/count:.1f}%)"

    return sampled, stats

def parse_dxf(file_path):
    import os
    output_path = file_path.replace('.dxf', '.json')

    doc = ezdxf.readfile(file_path)
    msp = doc.modelspace()
    entities = []

    for e in msp:
        layer = e.dxf.layer if hasattr(e.dxf, 'layer') else '0'
        etype = e.dxftype()

        if layer not in TIRE_LAYERS:
            continue
        if etype in EXCLUDE_TYPES:
            continue

        if etype == "LINE":
            entities.append({
                "type": "LINE",
                "layer": layer,
                "start": [e.dxf.start.x, e.dxf.start.y],
                "end": [e.dxf.end.x, e.dxf.end.y]
            })

        elif etype == "ARC":
            entities.append({
                "type": "ARC",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "radius": e.dxf.radius,
                "start_angle": e.dxf.start_angle,
                "end_angle": e.dxf.end_angle
            })

        elif etype == "CIRCLE":
            entities.append({
                "type": "CIRCLE",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "radius": e.dxf.radius
            })

        elif etype == "ELLIPSE":
            entities.append({
                "type": "ELLIPSE",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "major_axis": [e.dxf.major_axis.x, e.dxf.major_axis.y],
                "ratio": e.dxf.ratio,
                "start_param": e.dxf.start_param,
                "end_param": e.dxf.end_param
            })

        elif etype == "LWPOLYLINE":
            points = list(e.get_points('xy'))
            for i in range(len(points) - 1):
                entities.append({
                    "type": "LINE",
                    "layer": layer,
                    "start": list(points[i]),
                    "end": list(points[i + 1])
                })

        elif etype == "POLYLINE":
            points = [[v.dxf.location.x, v.dxf.location.y] for v in e.vertices]
            for i in range(len(points) - 1):
                entities.append({
                    "type": "LINE",
                    "layer": layer,
                    "start": points[i],
                    "end": points[i + 1]
                })

    sampled_entities, sample_stats = sample_entities(entities)

    layer_stats = {}
    type_stats = {}

    for entity in entities:
        layer = entity['layer']
        etype = entity['type']

        layer_stats[layer] = layer_stats.get(layer, 0) + 1
        type_stats[etype] = type_stats.get(etype, 0) + 1

    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(sampled_entities, f, indent=2, ensure_ascii=False)

    print(f"✅ 타이어 도면 추출 완료 → {output_path}")
    print(f"총 엔티티: {len(entities)}개 → 샘플: {len(sampled_entities)}개")
    print(f"\n📊 타입별 샘플링 (프로파일 우선):")
    for etype in sorted(sample_stats.keys()):
        print(f"  {etype}: {sample_stats[etype]}")
    print(f"\n📁 레이어별 원본 통계:")
    for layer, count in sorted(layer_stats.items(), key=lambda x: -x[1]):
        print(f"  {layer}: {count}개")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("❌ 사용법: python3 dxf_to_json.py [DXF 파일 경로]")
        sys.exit(1)
    parse_dxf(sys.argv[1])