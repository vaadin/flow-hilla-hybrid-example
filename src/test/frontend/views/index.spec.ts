import { test, expect } from '@playwright/test';

function getUrl() {
  return 'http://localhost:8080';
}

test.beforeEach(async ({ page }) => {
  await page.goto(getUrl());
  await page.waitForURL(getUrl());
  await page.waitForTimeout(300);
});

test('showld display a public message ', async ({ page }) => {
  await expect(await page.locator('p').first()).toBeVisible();
  await expect(page.locator('p')).toContainText('This is a Hilla view available publicly.');
});

