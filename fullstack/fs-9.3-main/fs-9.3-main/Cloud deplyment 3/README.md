
# Fullstack App - Deploy on AWS with Load Balancer

This project contains a minimal fullstack application (React frontend + Express backend) and instructions to deploy on AWS EC2 instances behind an Application Load Balancer (ALB).

## Project structure
- backend/       -> Node.js Express backend (port 5000)
- frontend/      -> React frontend (served by Nginx on port 80)
- docker-compose.yml -> For local testing

## Quick local run (requires Docker)
1. Build and run:
   docker-compose up --build
2. Open frontend: http://localhost:3000
   Backend API: http://localhost:5000/api/message

## Suggested AWS deployment steps (manual / high-level)
1. Create an ECR repository for frontend and backend, push Docker images there.
   - Build images locally:
     docker build -t fullstack-backend ./backend
     docker build -t fullstack-frontend ./frontend
   - Tag and push to ECR (follow AWS ECR docs to create repo and get login)
2. Launch EC2 instances (Amazon Linux 2 / Ubuntu) for backend (multiple instances for load balancing) and one for frontend (optional).
   - Attach IAM role to allow pulling from ECR if using private repos.
   - Security Group: allow HTTP (80) for frontend, HTTP (80) or custom port for ALB to backend, and SSH (22) from your IP.
3. On each backend EC2 instance, run Docker and start the backend container:
   sudo yum install -y docker
   sudo service docker start
   sudo docker run -d -p 5000:5000 --name backend fullstack-backend:latest
4. Set up an Application Load Balancer (ALB):
   - Create target group for backend instances (protocol HTTP, port 5000).
   - Register your backend EC2 instances with the target group.
   - Create ALB and add listener for port 80, forward to backend target group.
5. (Optional) Use Route 53 to point a domain to the ALB.
6. Test by accessing the ALB DNS name (or domain) in browser:
   - Frontend requests API at /api/message should route to ALB -> backend instances.
7. For production: consider auto-scaling group for backend instances, health checks (/health), SSL with ACM, and CI/CD using GitHub Actions to push images to ECR.

## Example User Data for EC2 (to run on boot)
This script will pull backend image from ECR and run it. Replace ECR URI placeholders.
```
#!/bin/bash
yum update -y
amazon-linux-extras install docker -y
service docker start
usermod -a -G docker ec2-user
# Login to ECR (replace region and account id)
aws ecr get-login-password --region us-east-1 | docker login --username AWS --password-stdin <AWS_ACCOUNT_ID>.dkr.ecr.us-east-1.amazonaws.com
docker pull <ECR_BACKEND_URI>:latest
docker run -d -p 5000:5000 --name backend <ECR_BACKEND_URI>:latest
```
