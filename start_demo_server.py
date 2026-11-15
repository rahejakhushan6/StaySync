#!/usr/bin/env python3
"""
StaySync Local Server & Preview
Serves the StaySync interactive web application and launches the browser automatically.
"""

import http.server
import socketserver
import os
import webbrowser
import sys

PORT = 8000
DIRECTORY = os.path.dirname(os.path.abspath(__file__))

class StaySyncHandler(http.server.SimpleHTTPRequestHandler):
    def __init__(self, *args, **kwargs):
        super().__init__(*args, directory=DIRECTORY, **kwargs)

    def do_GET(self):
        # Redirect root to live-app
        if self.path == '/' or self.path == '':
            self.send_response(302)
            self.send_header('Location', '/live-app/index.html')
            self.end_headers()
            return
        return super().do_GET()

def start_server():
    os.chdir(DIRECTORY)
    with socketserver.TCPServer(("", PORT), StaySyncHandler) as httpd:
        url = f"http://localhost:{PORT}/live-app/index.html"
        print("=" * 60)
        print(" StaySync Hostel Management System is RUNNING! ")
        print(f" URL: {url} ")
        print(" Press Ctrl+C to stop the server ")
        print("=" * 60)
        try:
            webbrowser.open(url)
        except Exception:
            pass
        try:
            httpd.serve_forever()
        except KeyboardInterrupt:
            print("\nShutting down StaySync demo server.")
            sys.exit(0)

if __name__ == "__main__":
    start_server()
