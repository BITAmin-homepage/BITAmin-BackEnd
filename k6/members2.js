import http from 'k6/http';
import { check } from 'k6';

export const options = {
  scenarios: {
    stress: {
      executor: 'ramping-vus',
      startVUs: 5,
      stages: [
        { duration: '10s', target: 10 }, // 워밍업
        { duration: '20s', target: 30 }, // 부하 증가 → 꼬리 발생 구간
        { duration: '10s', target: 10 }, // 회복
      ],
    },
  },

  thresholds: {
    http_req_duration: [
      'p(90)<300',
      'p(95)<500',   // 실패 확ㅣ
      'p(99)<1000',
    ],
    http_req_failed: ['rate<0.01'],
  },
};

export default function () {
  const res = http.get('https://api.bitamin.ai.kr/api/members/all');

  check(res, {
    'status is 200': (r) => r.status === 200,
  });
}