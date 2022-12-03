docker stop KMS-BE
docker rm KMS-BE
docker pull vanket/apartment-online-market:1.0.0
docker run -d --name KMS-BE -p 9090:9090  vanket/apartment-online-market:1.0.0