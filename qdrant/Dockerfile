FROM qdrant/qdrant:latest

WORKDIR /qdrant

COPY config.yaml /qdrant/config/production.yaml

CMD ["./qdrant", "--config-path", "/qdrant/config/production.yaml"]
