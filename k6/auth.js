import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
  vus: 10,        // 동시 사용자 수
  duration: '30s' // 30초 동안
};

export default function () {
  const res = http.get('https://localhost:8080/api/members/all');

  check(res, {
    'status is 200': (r) => r.status === 200,
    'response time < 500ms': (r) => r.timings.duration < 500,
  });

  sleep(1);
}