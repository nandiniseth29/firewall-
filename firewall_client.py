
import socket
import threading
from flask import Flask

ALLOWED_PORTS = [80, 443, 51857]

app = Flask(__name__)

def handle_connection(client_socket, address):
    try:
        data = client_socket.recv(1024)

        if address[1] not in ALLOWED_PORTS:
            print(f"Access denied to {address}")
            return

        print(f"Data received from {address}: {data.decode()}")
        client_socket.send(b"Thanks for your message!")

    except Exception as e:
        print(f"Error handling connection: {e}")

    finally:
        client_socket.close()

def start_firewall():
    server_socket = socket.socket(socket.AF_INET, socket.SOCK_STREAM)
    server_socket.bind(('0.0.0.0', 8888))
    server_socket.listen(5)

    print("Firewall is running. Waiting for connections...")

    while True:
        client_socket, addr = server_socket.accept()
        client_handler = threading.Thread(target=handle_connection, args=(client_socket, addr))
        client_handler.start()

if __name__ == "__main__":
    firewall_thread = threading.Thread(target=start_firewall)
    firewall_thread.start()

    
    @app.route('/')
    def home():
        return 'Welcome to the firewall-protected web server!'

    app.run(port=80)

