import { test, expect } from '@playwright/test';

function getUrl() {
  return 'http://localhost:8080';
}

test.beforeEach(async ({ page }) => {
  await page.goto(getUrl());
  await page.waitForURL(getUrl());
  await page.waitForLoadState();
});

test('showld display a public message ', async ({ page }) => {
  await page.waitForSelector('p');
  await expect(await page.locator('p').first()).toBeVisible();
  await expect(page.locator('p')).toContainText('This is a Hilla view available publicly.');
});

