import { test, expect } from '@playwright/test';

function getUrl() {
  return 'http://localhost:8080/about';
}

async function throttle(page) {
  const client = await page.context().newCDPSession(page);
  await client.send('Network.enable');
  await client.send('Network.emulateNetworkConditions', {
    offline: false,
    downloadThroughput: (500 * 1024) / 8, // 500 kbps
    uploadThroughput: (500 * 1024) / 8,
    latency: 400
  });
  await client.send('Emulation.setCPUThrottlingRate', {
    rate: 8
  });
  return 'http://localhost:8080/throttle';
}

test.beforeEach(async ({ page }) => {
  await page.goto(getUrl());
  await page.getByLabel('Username').fill("user");
  await page.getByLabel('Password', { exact: true }).fill("user");
  await page.getByRole('button', { name: 'Log in' }).click();
  await page.waitForURL(new RegExp(getUrl() + '.*'));
});

test('should display a view for authenticated users', async ({ page }) => {
  await page.waitForSelector('p');
  await expect(await page.locator('p')).toContainText('This is a Hilla view available for authenticated users.');
});

