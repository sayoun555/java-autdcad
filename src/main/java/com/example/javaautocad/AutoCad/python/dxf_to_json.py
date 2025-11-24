import ezdxf
import json
import sys

MAX_LINES = 10000

def parse_dxf(file_path):
    output_path = file_path.replace(".dxf", ".json")
    doc = ezdxf.readfile(file_path)
    msp = doc.modelspace()
    entities = []

    line_buffer = []
    curve_buffer = []

    for e in msp:
        layer = e.dxf.layer if hasattr(e.dxf, "layer") else "0"
        etype = e.dxftype()

        if etype == "ARC":
            curve_buffer.append({
                "type": "ARC",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "radius": e.dxf.radius,
                "start_angle": e.dxf.start_angle,
                "end_angle": e.dxf.end_angle
            })
            continue

        if etype == "CIRCLE":
            curve_buffer.append({
                "type": "CIRCLE",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "radius": e.dxf.radius
            })
            continue

        if etype == "ELLIPSE":
            curve_buffer.append({
                "type": "ELLIPSE",
                "layer": layer,
                "center": [e.dxf.center.x, e.dxf.center.y],
                "major_axis": [e.dxf.major_axis.x, e.dxf.major_axis.y],
                "ratio": e.dxf.ratio,
                "start_param": e.dxf.start_param,
                "end_param": e.dxf.end_param
            })
            continue

        if etype == "LINE":
            line_buffer.append({
                "type": "LINE",
                "layer": layer,
                "start": [e.dxf.start.x, e.dxf.start.y],
                "end": [e.dxf.end.x, e.dxf.end.y]
            })
            continue

        if etype == "LWPOLYLINE":
            pts = [[p[0], p[1]] for p in e.get_points("xy")]
            for i in range(len(pts) - 1):
                line_buffer.append({
                    "type": "LINE",
                    "layer": layer,
                    "start": pts[i],
                    "end": pts[i + 1]
                })
            continue

        if etype == "POLYLINE":
            pts = [[v.dxf.location.x, v.dxf.location.y] for v in e.vertices]
            for i in range(len(pts) - 1):
                line_buffer.append({
                    "type": "LINE",
                    "layer": layer,
                    "start": pts[i],
                    "end": pts[i + 1]
                })
            continue

    if len(line_buffer) > MAX_LINES:
        line_buffer = line_buffer[:MAX_LINES]

    entities.extend(curve_buffer)
    entities.extend(line_buffer)

    with open(output_path, "w", encoding="utf-8") as f:
        json.dump(entities, f, indent=2, ensure_ascii=False)

    print(f"완료 → {output_path}")
    print(f"곡선: {len(curve_buffer)}, 선: {len(line_buffer)}, 총: {len(entities)}")

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print("사용법: python3 dxf_to_json.py [DXF 파일]")
        sys.exit(1)
    parse_dxf(sys.argv[1])
